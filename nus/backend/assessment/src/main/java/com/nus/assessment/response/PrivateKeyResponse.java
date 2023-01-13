package com.nus.assessment.response;

import java.security.PrivateKey;

import lombok.Data;

@Data
public class PrivateKeyResponse {
    private String private64;
    private PrivateKey privateKey;

    public PrivateKeyResponse() {}

    public PrivateKeyResponse(String private64, PrivateKey privateKey) {
        this.private64 = private64;
        this.privateKey = privateKey;
    }

}
