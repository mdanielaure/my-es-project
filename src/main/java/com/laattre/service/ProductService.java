package com.laattre.service;

import java.util.List;
import java.util.Optional;

import com.laattre.persistence.model.Product;

public interface ProductService {
	
	Product save(Product product);

	List<Product> findAll();
	
	Optional<Product> findOne(Long id);
	
	void removeOne(Long id);
}
