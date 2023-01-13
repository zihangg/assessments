package com.nus.assessment.response;


import lombok.Data;

@Data
public class AccountResponse {
    private String accountId;
    private String privateKey;
    private String publicKey;

    // constructors
    public AccountResponse() {}

    public AccountResponse(String accountId, String privateKey, String publicKey) {
        this.accountId = accountId;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}

