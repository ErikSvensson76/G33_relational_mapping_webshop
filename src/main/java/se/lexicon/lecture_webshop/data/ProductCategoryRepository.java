package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;

import se.lexicon.lecture_webshop.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

}
