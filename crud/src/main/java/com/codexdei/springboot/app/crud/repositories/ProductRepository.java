package com.codexdei.springboot.app.crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codexdei.springboot.app.crud.entities.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{

    boolean existsBySku(String sku);

}
