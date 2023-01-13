package com.nus.assessment.utils;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequiredFieldsUtils {
    public void printRequiredFields(int txId, long timestamp, Object body, String public64, String sig64) {
        log.info("--------------------------------------------------------------------------------------------------------------------------------");
        log.info("ID: " + txId);
        log.info("Timestamp: " + timestamp);
        log.info("Params received: " + body);
        log.info("Public Key: " + public64);
        log.info("Signature in Base64: " + sig64);
        log.info("--------------------------------------------------------------------------------------------------------------------------------");
    }
}
