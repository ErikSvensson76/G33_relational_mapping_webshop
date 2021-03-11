package se.lexicon.lecture_webshop;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import se.lexicon.lecture_webshop.data.ProductCategoryRepository;
import se.lexicon.lecture_webshop.data.ProductRepository;
import se.lexicon.lecture_webshop.entity.Product;
import se.lexicon.lecture_webshop.entity.ProductCategory;

@Component
public class CommandLine implements CommandLineRunner {
	
	private final ProductRepository productRepository;
	private final ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	public CommandLine(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Product product = new Product(null, "Laser Rifle", BigDecimal.valueOf(99.99), null);
		ProductCategory category = new ProductCategory();
		category.setValue("Futuristic");
		
		category = productCategoryRepository.save(category);
		product.addCategories(category);		
		product = productRepository.save(product);
		
		System.out.println(product);
		
		System.out.println("*****");
		
		productRepository.findByCategoriesValueIgnoreCase("FuturistIc")
			.stream()
			.forEach(System.out::println);
		
		System.out.println("*****");
		
		System.out.println(productRepository.findByName("Laser Rifle"));
		
		
		
		
		
	}

}
