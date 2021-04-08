package se.lexicon.lecture_webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import se.lexicon.lecture_webshop.dto.ProductCategoryDTO;
import se.lexicon.lecture_webshop.dto.ProductDTO;
import se.lexicon.lecture_webshop.entity.Product;
import se.lexicon.lecture_webshop.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
class ProductControllerTest {

    public static final String API_V_1_PRODUCTS_ID = "/api/v1/products/{id}";
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Autowired private TestEntityManager em;
    @Autowired private WebApplicationContext webApplicationContext;

    public List<Product> getProducts(){
        return Arrays.asList(
                new Product(null, "Test 1", BigDecimal.valueOf(19.90), null),
                new Product(null, "Test 2", BigDecimal.valueOf(20.90), null),
                new Product(null, "Test 3", BigDecimal.valueOf(99.50), null)
        );
    }

    public List<ProductCategory> getCategories(){
        return Arrays.asList(
                new ProductCategory(null, "category 1", null),
                new ProductCategory(null, "category 2", null),
                new ProductCategory(null, "category 3", null)
        );
    }

    List<Product> persistedProducts;
    List<ProductCategory> persistedCategories;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        persistedCategories = new ArrayList<>();

        for(ProductCategory category : getCategories()){
            persistedCategories.add(em.persist(category));
        }

        persistedProducts = new ArrayList<>();
        for(Product product : getProducts()){
            product.addCategories(persistedCategories.get(0));
            persistedProducts.add(em.persist(product));
        }

        objectMapper = new ObjectMapper();
    }

    @Test
    void context() {
        assertNotNull(mockMvc);
        assertNotNull(em);
        assertNotNull(webApplicationContext);
        assertNotNull(objectMapper);
    }

    @Test
    @DisplayName("Given product id and valid URI should return status 200 and contain correct information")
    void findById() throws Exception {
        String productId = persistedProducts.get(0).getProductId();
        String URI = API_V_1_PRODUCTS_ID;

        mockMvc.perform(get(URI, productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productName").value(persistedProducts.get(0).getProductName()))
                .andExpect(jsonPath("$.productPrice").value(persistedProducts.get(0).getProductPrice()));


    }

    @Test
    @DisplayName("Given invalid id and valid URI should return status 404 and contain error response")
    void findById_not_found() throws Exception {
        String invalidId = "foo";
        String URI = API_V_1_PRODUCTS_ID;

        mockMvc.perform(get(URI, invalidId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Given valid form and URI create should persist and return 201")
    void create_success() throws Exception{
        ProductDTO form = new ProductDTO();
        ProductCategoryDTO categoryDTO = new ProductCategoryDTO();
        categoryDTO.setCategoryId(persistedCategories.get(1).getCategoryId());
        categoryDTO.setValue(persistedCategories.get(1).getValue());
        form.setProductName("Plasma Rifle");
        form.setProductPrice(BigDecimal.valueOf(199999.90));
        form.setCategories(Collections.singletonList(categoryDTO));

        String jsonForm = objectMapper.writeValueAsString(form);
        String URI = "/api/v1/products";

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonForm))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").isNotEmpty());
    }

    @Test
    @DisplayName("Given search = 'category_name' and value = 'category 1' return status 200")
    void find_category_name() throws Exception {
        String search = "category_name";
        String value = "category 1";

        mockMvc.perform(get("/api/v1/products").param("search", search).param("value", value))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}