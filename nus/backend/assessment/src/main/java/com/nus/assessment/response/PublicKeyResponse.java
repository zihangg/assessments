package com.nus.assessment.response;

import java.security.PublicKey;

import lombok.Data;

@Data
public class PublicKeyResponse {
    private String public64;
    private PublicKey publicKey;

    public PublicKeyResponse() {}

    public PublicKeyResponse(String public64, PublicKey publicKey) {
        this.public64 = public64;
        this.publicKey = publicKey;
    }
}
