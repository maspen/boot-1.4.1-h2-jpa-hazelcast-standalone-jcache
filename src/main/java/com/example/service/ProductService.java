package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;

/**
 * Annotations are JSR-107 annotations; not spring cache
 * 
 * @author maspen
 *
 */
@Service
public class ProductService {

	private final ProductRepository repository;
	
	private final CacheManager cacheManager;
	private final Cache cache;
	
	@Autowired
	public ProductService(ProductRepository repository, CacheManager cacheManager) {
		this.repository = repository;
		this.cacheManager = cacheManager;
		this.cache = cacheManager.getCache("product-cache");
	}
	
	@CacheResult(cacheName = "product-cache")
	public Product getProductById(@CacheKey Long id) {
		System.out.println("ProductService#getProductById");
		makeSlow();
		
		return repository.getProductById(id);
	}
	
	@CacheResult(cacheName = "product-cache")
	public List<Product> findAll() {
		System.out.println("ProductService#findAll");
		makeSlow();
		
//		Iterable<Product> productIterable = repository.findAll();
//		List<Product> productList = new ArrayList<Product>();
//		for (Product product : productIterable) {
//			
//			productList.add(product);
//		}
		
		List<Product> productList = (List<Product>) repository.findAll();
		
		return productList;
	}

	//@CachePut(cacheName = "product-cache")
	// commenting out b/c need to update cache
	public Product save(@CacheValue Product product) {
		System.out.println("ProductService#save");
		makeSlow();
		
		Product persistedProduct = repository.save(product);
		cache.put(persistedProduct.getId(), persistedProduct);
		
		return persistedProduct;
	}

	@CacheRemove(cacheName = "product-cache")
	public void deleteById(@CacheKey Long id) {
		System.out.println("ProductService#deleteById");
		makeSlow();
		
		repository.delete(id);
	}
	
	/**
	 * to simulate latency
	 */
	private void makeSlow() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.err.println("exception in makeSlow() " + e.getMessage());
		}
	}
}
