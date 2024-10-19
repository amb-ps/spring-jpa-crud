package com.eca.crud.example.service;

import com.eca.crud.example.entity.Product;
import com.eca.crud.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Entity with ID " + id + " not found"));
    }

    public Product getProductByName(String name) {
        return repository.findByName(name);
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId())
                .orElseThrow(() -> new BadRequestException("Entity with ID " + product.getId() + " not found"));
        Optional.ofNullable(product.getName())
                .ifPresent(existingProduct::setName);

        Optional.ofNullable(product.getQuantity())
                .filter(q -> q > 0)
                .ifPresent(existingProduct::setQuantity);

        Optional.ofNullable(product.getPrice())
                .filter(p -> p > 0)
                .ifPresent(existingProduct::setPrice);
        return repository.save(existingProduct);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    static
    class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }

}
