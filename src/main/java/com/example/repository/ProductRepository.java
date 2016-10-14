package com.example.repository;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

//	@CacheResult(cacheName = "product-cache")
//	public Product getProductById(@CacheKey Long id);
//	
//	@CacheResult(cacheName = "product-cache")
//	public Iterable<Product> findAll();
//	
//	@CachePut(cacheName = "product-cache")
//	public Product save(@CacheValue Product product);
//	
//	@CacheRemove(cacheName = "product-cache")
//	public void deleteById(@CacheKey Long id);
	
	public Product getProductById(Long id);
	
	public Iterable<Product> findAll();
	
	public Product save(Product product);
	
	public void deleteById(Long id);
}
