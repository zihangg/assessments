package com.nus.assessment.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.nus.assessment.model.Account;
import com.nus.assessment.model.GetBalanceParam;
import com.nus.assessment.model.SendParam;
import com.nus.assessment.repository.AccountRepository;
import com.nus.assessment.response.AccountResponse;
import com.nus.assessment.response.GetBalanceResponse;
import com.nus.assessment.response.PrivateKeyResponse;
import com.nus.assessment.response.PublicKeyResponse;
import com.nus.assessment.response.SendResponse;
import com.nus.assessment.utils.RequiredFieldsUtils;
import com.nus.assessment.utils.SignatureUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private ContractService contractService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SignatureUtils signatureUtils;

    @Autowired
    private RequiredFieldsUtils requiredFieldsUtils;

    private Integer txId = 0;

    public ResponseEntity<AccountResponse> createAccount(String accountId) throws Exception {
        log.info("Create account request received. Creating account...");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        keyGen.initialize(2048, random);
        KeyPair keyPair = keyGen.generateKeyPair();

        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();

        // encode to base 64
        String private64 = Base64.getEncoder().encodeToString(privateKey);
        String public64 = Base64.getEncoder().encodeToString(publicKey);

        
        TransactionReceipt tx = contractService.storePublicKey(accountId, public64);
        log.info("TX sent @ " + tx.getTransactionHash());

        Account account = new Account(accountId, private64);
    
        accountRepository.save(account);
        
        AccountResponse res = new AccountResponse();
        res.setAccountId(accountId);
        res.setPrivateKey(private64);
        res.setPublicKey(public64);

        log.info("Account created.");

        return ResponseEntity.ok().body(res);
    }

    public ResponseEntity<SendResponse> send(SendParam body) throws Exception {
        log.info("Send request received...");
        
        // increment tx count
        txId++;

        // timestamp
        long timestamp = System.currentTimeMillis();

        // retrieve public key
        PublicKeyResponse publicKeys = getPublicKey(body.getFrom()); 
        String public64 = publicKeys.getPublic64();
        PublicKey publicKey = publicKeys.getPublicKey();

        // retrieve private key
        PrivateKeyResponse privateKeys = getPrivateKey(body.getFrom());
        PrivateKey privateKey = privateKeys.getPrivateKey();

        // build string
        String params = txId.toString() + timestamp + "from" + body.getFrom() + "to" + body.getTo() + "amount" + body.getAmount() + public64;

        // encrypt
        byte[] paramBytes = params.getBytes();
        byte[] signature = signatureUtils.encrypt(paramBytes, privateKey);
        String sig64 = Base64.getEncoder().encodeToString(signature);

        // console log all required fields
        requiredFieldsUtils.printRequiredFields(txId, timestamp, body, public64, sig64);

        // verify keys match
        boolean result = signatureUtils.verify(paramBytes, publicKey, signature);


        // build tx and send tx
        SendResponse res = new SendResponse();
        res.setId(txId);

        if (result) {
            log.info("Verification successful. Sending TX...");
            try {
                TransactionReceipt tx = contractService.callSend(body.getFrom(), body.getTo(), body.getAmount());
                log.info("TX sent @ " + tx.getTransactionHash());
                if (tx != null) {
                    res.setMessage("TX successfully sent!");
                    res.setStatus(HttpStatus.OK);
                }
            }
            catch (Exception e) {
                res.setMessage("TX reverted!");
                res.setStatus(HttpStatus.BAD_REQUEST);
            }
        } else {
            res.setMessage("Verification failed!");
            res.setStatus(HttpStatus.BAD_REQUEST);
        }

        log.info("Send request completed.");
        return ResponseEntity.status(res.getStatus()).body(res);
    }

    public ResponseEntity<GetBalanceResponse> getBalance(String accountId) throws Exception {
        log.info("Get balance request received...");

        // increment tx count
        txId++;

        // timestamp
        long timestamp = System.currentTimeMillis();

        // retrieve public key
        PublicKeyResponse publicKeys = getPublicKey(accountId); 
        String public64 = publicKeys.getPublic64();
        PublicKey publicKey = publicKeys.getPublicKey();

        // retrieve private key
        PrivateKeyResponse privateKeys = getPrivateKey(accountId);
        PrivateKey privateKey = privateKeys.getPrivateKey();

        String params = txId.toString() + timestamp + "accountId" + accountId + public64;
        GetBalanceParam getBalanceParam = new GetBalanceParam(accountId);

        byte[] paramBytes = params.getBytes();
        byte[] signature = signatureUtils.encrypt(paramBytes, privateKey);
        String sig64 = Base64.getEncoder().encodeToString(signature);

        // console log all required fields
        requiredFieldsUtils.printRequiredFields(txId, timestamp, getBalanceParam, public64, sig64);

        // verify keys match
        boolean result = signatureUtils.verify(paramBytes, publicKey, signature);

        GetBalanceResponse res = new GetBalanceResponse();
        res.setId(txId);

        if (result) {
            log.info("Verification successful. Requesting balance...");
            try {
                Double balance = contractService.getBalance(accountId);
                res.setAmount(balance);
                res.setStatus(HttpStatus.OK);
            } catch (Exception e) {
                res.setStatus(HttpStatus.BAD_REQUEST);
            }
        } else {
            res.setStatus(HttpStatus.BAD_REQUEST);
        }

        log.info("Get balance request completed.");
        return ResponseEntity.status(res.getStatus()).body(res);
    }


    public List<Account> getAccounts() throws Exception {
        return accountRepository.findAll();
    }

    public Account getAccount(String accountId) throws Exception {
        return accountRepository.findByAccountId(accountId);
    }

    private PublicKeyResponse getPublicKey(String accountId) throws Exception {
        String public64 = contractService.getPublicKey(accountId);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] publicBytes = Base64.getDecoder().decode(public64);
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicBytes));

        PublicKeyResponse publicKeyResponse = new PublicKeyResponse(public64, publicKey);

        return publicKeyResponse;
    }

    private PrivateKeyResponse getPrivateKey(String accountId) throws Exception {
        Account account = getAccount(accountId);

        String private64 = account.getPrivateKey();

        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] privateBytes = Base64.getDecoder().decode(private64);
        PrivateKey privateKey =  kf.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));

        PrivateKeyResponse privateKeyResponse = new PrivateKeyResponse(private64, privateKey);

        return privateKeyResponse;
    }
}
