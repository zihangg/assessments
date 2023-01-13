package com.nus.assessment.model;

import lombok.Data;

@Data
public class GetBalanceParam {
    String accountId;

    public GetBalanceParam() {}

    public GetBalanceParam(String accountId) {
        this.accountId = accountId;
    }
}
