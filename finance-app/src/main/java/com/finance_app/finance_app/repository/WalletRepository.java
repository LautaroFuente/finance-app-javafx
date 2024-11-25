package com.finance_app.finance_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance_app.finance_app.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{

}
