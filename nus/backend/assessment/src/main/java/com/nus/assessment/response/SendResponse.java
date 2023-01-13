package com.nus.assessment.response;

import org.springframework.http.HttpStatusCode;

import lombok.Data;

@Data
public class SendResponse {
    HttpStatusCode status;
    String message;
    Integer id;
}
