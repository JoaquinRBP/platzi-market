package com.platzi.market.domain.service;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getAll(){
        return productRepository.getAll();
    }
    public Optional<Product> getProduct(Long productId){
        return productRepository.getProduct(productId);
    }
    public Optional<List<Product>> getByCategory(Long categoryId){
        return productRepository.getByCategory(categoryId);
    }
    public Product save(Product product){
        return productRepository.save(product);
    }
    public boolean delete(Long productId){
       return getProduct(productId).map(product -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);
        /*if(getProduct(productId).isPresent()){
            productRepository.delete(productId);
            return true;
        }
        else{
            return false;
        }*/
    }
}