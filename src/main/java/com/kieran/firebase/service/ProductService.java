package com.kieran.firebase.service;

import com.kieran.firebase.entity.Product;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductService {

    List<Product> getAllProducts() throws ExecutionException, InterruptedException;

    Product getProductDetails(String name) throws ExecutionException, InterruptedException;

    String saveProduct(Product product) throws ExecutionException, InterruptedException;

    String updateProduct(Product product) throws ExecutionException, InterruptedException;

    String deleteProduct(String name) throws ExecutionException, InterruptedException;

}
