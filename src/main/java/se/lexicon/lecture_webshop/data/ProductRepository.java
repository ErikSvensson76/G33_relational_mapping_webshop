package se.lexicon.lecture_webshop.data;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.lexicon.lecture_webshop.entity.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
	
	Collection<Product> findByCategoriesValueIgnoreCase(String value);
	@Query("SELECT p FROM Product p WHERE UPPER(p.productName) LIKE UPPER(:productName)")
	Collection<Product> findByName(@Param("productName")String productName);

}
