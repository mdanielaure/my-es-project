package com.laattre.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laattre.service.ProductService;
import com.laattre.persistence.dao.ProductRepository;
import com.laattre.persistence.model.Product;



@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}
	
	public Optional<Product> findOne(Long id) {
		return productRepository.findById(id);
	}
	
	public void removeOne(Long id) {
		productRepository.deleteById(id);
	}
}
