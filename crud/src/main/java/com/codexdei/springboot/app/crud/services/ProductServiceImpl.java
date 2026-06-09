package com.codexdei.springboot.app.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codexdei.springboot.app.crud.entities.Product;
import com.codexdei.springboot.app.crud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {

        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {

        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {

        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {

        
        Optional<Product> productOptional = productRepository.findById(id);
        
        if (productOptional.isPresent()) {
            
            Product productDb = productOptional.get();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setDescription(product.getDescription());
            productDb.setSku(product.getSku());
            productRepository.save(productDb);

            return Optional.of(productDb);

        } else {
            System.out.println("Product not found");
            return productOptional;
        }


    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        productOptional.ifPresentOrElse(productDb -> {

            productRepository.delete(productDb);

        }, () -> System.out.println("Producto not found"));

        return productOptional;

    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {

        return productRepository.existsBySku(sku);
    }

}
