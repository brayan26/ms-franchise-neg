package com.reactive.nequi.command;

import com.reactive.nequi.model.Product;

public record ProductUpdaterCommand(Long id, Product product) {
}
