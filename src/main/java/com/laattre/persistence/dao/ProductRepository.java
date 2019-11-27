package com.laattre.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.laattre.persistence.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
