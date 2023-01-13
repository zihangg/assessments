package com.nus.assessment.model;

import lombok.Data;

@Data
public class SendParam {
    private String from;
    private String to;
    private Double amount;
}
