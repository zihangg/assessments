package com.nus.assessment.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import com.nus.assessment.wrappers.ContractWrapper;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ContractService {
    final Web3j client = Web3j.build(new HttpService("https://eth-goerli.g.alchemy.com/v2/4Q_b53AKgZHkK546yLwqsQP90HiS8lUl"));

    @Value("${app.contract.address}")
    private String contractAddress;

    @Value("${app.private.key}")
    private String privateKey;

    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(1000000);
    public static final BigInteger GAS_PRICE = Convert.toWei("200", Convert.Unit.GWEI).toBigInteger();

    private ContractWrapper contract;

    public void deployContract() throws Exception {
        log.info("Deploying contract...");
        try {
            FastRawTransactionManager fastRawTransactionManager = new FastRawTransactionManager(client, Credentials.create(privateKey));
            // contract = ContractWrapper.deploy(client, Credentials.create(privateKey), new StaticGasProvider(GAS_PRICE, GAS_LIMIT)).send();
            contract = ContractWrapper.load("0xFF1e5Ee81595b69bE0C5baEd0ae93f4450BBeDd1", client, fastRawTransactionManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));
            log.info("Contract successfully deployed at: " + contract.getContractAddress());
        } catch (Exception e) {
            log.error("An error occured: " + e);
            throw e;
        }
    }

    public Double getBalance(String accountId) throws Exception {
        BigInteger balance;
        try {
            balance = contract.getBalance(accountId).send();
        } catch (Exception e) {
            log.error("An error occured: " + e);
            throw e;
        }

        return balance.doubleValue();
    }

    public String getPublicKey(String accountId) throws Exception {
       String publicKey = contract.getKeys(accountId).send();

       return publicKey;
    }

    public TransactionReceipt storePublicKey(String accountId, String publicKey) throws Exception {
        TransactionReceipt txReceipt = contract.saveKey(accountId, publicKey).send();

        return txReceipt;
    }

    public TransactionReceipt callSend(String sender, String recipient, Double amount) throws Exception {
        BigInteger bigAmount = BigDecimal.valueOf(amount).toBigInteger();
        TransactionReceipt txReceipt = contract.send(sender, recipient, bigAmount).send();

        return txReceipt;
    }

}
