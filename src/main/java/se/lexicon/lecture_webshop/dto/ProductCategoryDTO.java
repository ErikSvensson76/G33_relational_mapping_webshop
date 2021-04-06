package se.lexicon.lecture_webshop.dto;

import java.io.Serializable;

public class ProductCategoryDTO implements Serializable {

    private String categoryId;
    private String value;

    public ProductCategoryDTO() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
