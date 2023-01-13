package com.nus.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nus.assessment.model.Account;
import com.nus.assessment.model.AccountParam;
import com.nus.assessment.model.SendParam;
import com.nus.assessment.response.AccountResponse;
import com.nus.assessment.response.GetBalanceResponse;
import com.nus.assessment.response.GenericErrorResponse;
import com.nus.assessment.response.SendResponse;
import com.nus.assessment.service.AccountService;

@RestController
@RequestMapping("accounts")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @GetMapping("list")
    public List<Account> getAccounts() throws Exception {
        return accountService.getAccounts();
    }

    @GetMapping()
    public Account getAccount(@RequestParam String accountId) throws Exception {
        return accountService.getAccount(accountId);
    }

    @GetMapping("get-balance")
    public ResponseEntity<GetBalanceResponse> getBalance(@RequestParam String accountId) throws Exception {
        return accountService.getBalance(accountId);
    }

    @PostMapping("create-account")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountParam body) throws Exception {
        return accountService.createAccount(body.getAccountId());
    }

    @Async
    @PostMapping("send")
    public ResponseEntity<SendResponse> send(@RequestBody SendParam body) throws Exception {
        return accountService.send(body);
    }


    // handling some exceptions
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericErrorResponse> handleMissingParams(MissingServletRequestParameterException e) {
        GenericErrorResponse res = new GenericErrorResponse();
        String name = e.getParameterName();
        res.setError(name + " parameter is missing");
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericErrorResponse> handleConflict(DataIntegrityViolationException e) {
        GenericErrorResponse res = new GenericErrorResponse();
        String msg = e.getMessage();
        res.setError(msg);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<GenericErrorResponse> handleNullPointer(NullPointerException e) {
        GenericErrorResponse res = new GenericErrorResponse();
        String msg = e.getMessage();
        res.setError(msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    
}
