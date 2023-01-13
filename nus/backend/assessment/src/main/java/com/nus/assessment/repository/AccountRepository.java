package com.nus.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nus.assessment.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    Account findByAccountId(String accountId);
}
