package com.finance_app.finance_app.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.entities.Wallet;
import com.finance_app.finance_app.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;
	
	public String addWallet(User user) {
		
		Wallet wallet = new Wallet(user, new BigDecimal(0.00));
		
		try {
			Wallet walletSaved = this.walletRepository.save(wallet);
			
			if(walletSaved != null) {
				return "Billetera creada exitosamente";
			}
			else {
				throw new Error("Error al crear la billetera");
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public Wallet getOneWallet(Long userId) {
		return this.walletRepository.getOneWallet(userId);
	}
	
	public void updateWalletTotalWithIncome(Long idUser, BigDecimal amount) {
		this.walletRepository.updateWalletTotalWithIncome(idUser, amount);
	}
	
	public void updateWalletTotalWithExpense(Long idUser, BigDecimal amount) {
		this.walletRepository.updateWalletTotalWithExpense(idUser, amount);
	}
}
