package se.lexicon.lecture_webshop.service.interfaces;

import se.lexicon.lecture_webshop.dto.ProductDTO;

import java.util.Collection;

public interface ProductService extends GenericCRUD<String, ProductDTO>{
    Collection<ProductDTO> findByCategoryName(String categoryName);
    Collection<ProductDTO> findByProductName(String productName);
    ProductDTO addProductCategory(String productId, String categoryId);
    ProductDTO removeProductCategory(String productId, String categoryId);
}
