package com.nus.assessment.utils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.springframework.stereotype.Component;

@Component
public class SignatureUtils {
    public byte[] encrypt(byte[] paramBytes, PrivateKey privateKey) throws Exception {
        Signature sign = Signature.getInstance("SHA256WithRSA");
        sign.initSign(privateKey);
        sign.update(paramBytes);
        byte[] signature = sign.sign();

        return signature;
    }

    public boolean verify(byte[] paramBytes, PublicKey publicKey, byte[] signature) throws Exception {
        Signature sign = Signature.getInstance("SHA256WithRSA");
        sign.initVerify(publicKey);
        sign.update(paramBytes);
        boolean result = sign.verify(signature);

        return result;
    }
}
