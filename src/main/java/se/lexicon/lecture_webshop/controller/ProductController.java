package se.lexicon.lecture_webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.lecture_webshop.dto.ProductCategoryDTO;
import se.lexicon.lecture_webshop.dto.ProductDTO;
import se.lexicon.lecture_webshop.service.interfaces.ProductCategoryService;
import se.lexicon.lecture_webshop.service.interfaces.ProductService;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    //Create
    @PostMapping("/api/v1/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO form){
        if(form == null) throw new IllegalArgumentException("RequestBody was null");

        ProductDTO result = productService.create(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //Read
    @GetMapping("/api/v1/products")
    public ResponseEntity<?> find(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "value", defaultValue = "all") String value
    ){

        switch (search.trim()){
            case "category_name":
                return ResponseEntity.ok(productService.findByCategoryName(value.trim()));
            case "product_name":
                return ResponseEntity.ok(productService.findByProductName(value.trim()));
            case "all":
                return ResponseEntity.ok(productService.findAll());
            default:
                throw new IllegalArgumentException("Invalid search type: " + search + ". Valid search types are 'category_name' and 'product_name'");
        }
    }

    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") String id){
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok(productDTO);
    }

    //Update

    @PutMapping("/api/v1/products/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") String id, @RequestBody ProductDTO form){
        if(!id.equals(form.getProductId())){
            throw new IllegalArgumentException("PathVariable id didnt match form.productId");
        }

        ProductDTO productDTO = productService.update(id, form);

        return ResponseEntity.ok(productDTO);
    }


    @PutMapping("/api/v1/products/{id}/categories")
    public ResponseEntity<ProductDTO> handleCategory(
            @PathVariable("id") String productId,
            @RequestBody ProductCategoryDTO categoryDTO,
            @RequestParam(name = "action") String action
    ){

        ProductDTO productDTO;

        switch (action.trim().toLowerCase()){
            case "add":
                String categoryId;
                if(categoryDTO.getCategoryId() == null){
                    ProductCategoryDTO dto = productCategoryService.create(categoryDTO);
                    categoryId = dto.getCategoryId();
                }else {
                    categoryId = categoryDTO.getCategoryId();
                }
                productDTO = productService.addProductCategory(productId, categoryId);
                break;
            case "remove":
                productDTO = productService.removeProductCategory(productId, categoryDTO.getCategoryId());
                break;
            default:
                throw new IllegalArgumentException("Invalid action " + action);
        }

        return ResponseEntity.ok(productDTO);
    }
}
