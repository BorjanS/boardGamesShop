package com.board_games_shop.board_games_shop.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(int name) {
        super(String.format("Product %s is out of stock!", name));
    }
}
