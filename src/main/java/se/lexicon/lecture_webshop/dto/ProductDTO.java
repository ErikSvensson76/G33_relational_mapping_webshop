package se.lexicon.lecture_webshop.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

public class ProductDTO implements Serializable {
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Collection<ProductCategoryDTO> categories;

    public ProductDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Collection<ProductCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Collection<ProductCategoryDTO> categories) {
        this.categories = categories;
    }
}
