package com.nus.assessment.response;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class GetBalanceResponse {
    HttpStatusCode status;
    Double amount;
    Integer id;
}
