package se.lexicon.lecture_webshop.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.lexicon.lecture_webshop.entity.Product;
import se.lexicon.lecture_webshop.entity.ProductCategory;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired private ProductRepository testObject;
	@Autowired private TestEntityManager em;
	
	List<ProductCategory> categories;
	
	public List<ProductCategory> getCategories(){
		return Arrays.asList(
				new ProductCategory(null, "cat1", null),
				new ProductCategory(null, "cat2", null),
				new ProductCategory(null, "cat3", null)
				
		);
	}
	
	@BeforeEach
	public void setup() {
		categories = getCategories().stream()
				.map(em::persist)
				.collect(Collectors.toList());
		
		Product p1 = new Product();
		Product p2 = new Product();
		
		p1.setProductName("Test 1");
		p2.setProductName("Test 2");
		p1.addCategories(categories.get(0), categories.get(2));
		p2.addCategories(categories.get(1), categories.get(2));
		
		em.persist(p1);
		em.persist(p2);
		
		em.flush();		
	}
	
	@Test
	@DisplayName("given 'cat3' findByCategoriesValueIgnoreCase return collection of 2 elements")
	public void findByCategoriesValueIgnoreCase() {
		String param = "cat3";
		
		Collection<Product> result = testObject.findByCategoriesValueIgnoreCase(param);
		
		assertEquals(2, result.size());
	}
	
	@Test
	@DisplayName("given 'Test 1' findByName return collection with size 1")
	public void findByName() {
		int expected = 1;
		String param ="Test 1";
		
		Collection<Product> result = testObject.findByName(param);
		
		assertEquals(expected, result.size());
		
	}

}
