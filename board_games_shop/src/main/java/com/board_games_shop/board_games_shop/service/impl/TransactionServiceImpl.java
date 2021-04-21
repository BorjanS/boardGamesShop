package com.board_games_shop.board_games_shop.service.impl;

import com.board_games_shop.board_games_shop.model.Transaction;
import com.board_games_shop.board_games_shop.repository.TransactionRepository;
import com.board_games_shop.board_games_shop.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void saveTransaction(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }
}

