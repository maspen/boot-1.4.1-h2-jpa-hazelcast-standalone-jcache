package com.example.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.service.ProductService;
import com.hazelcast.internal.util.ThreadLocalRandom;

@RequestMapping("/products")
@RestController
public class ProductController {
	
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
    @RequestMapping(value = "/getProduct/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") Long id) {
      long start = System.nanoTime();
      Product product = productService.getProductById(id);
      
      System.out.println(String.format("getProduct(id) took: %s millis", (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start))));
      
      return product;
    }
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		long start = System.nanoTime();


		List<Product> productList = productService.findAll();
		
		System.out.println(String.format("findAll() took: %s millis", (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start))));

		// java.lang.ClassCastException: com.example.entity.Product cannot be cast to java.lang.Iterable
		//productService.findAll().forEach(productList::add);
//		List<Product> productList = new ArrayList<>();
//		for (Product product : productIterable) {
//			productList.add(product);
//		}
		
		return productList;
	}	
	
    @RequestMapping(value = "/save/", method = RequestMethod.PUT)
    public Product save(@RequestBody Product product) {
    	long start = System.nanoTime();
    	
    	//Product savedProduct = productService.save(generateKey(), product);
    	Product savedProduct = productService.save(product);
    	
    	System.out.println(String.format("save(product) took: %s millis", (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start))));
    	
    	return savedProduct;
    }
	
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
    	long start = System.nanoTime();
    	
    	productService.deleteById(id);
    	
    	System.out.println(String.format("delete(id) took: %s millis", (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start))));
    }
    
    private Long generateKey() {
    	return new Long(ThreadLocalRandom.current().nextLong(100));
    }
}
