package se.lexicon.lecture_webshop.service.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.lecture_webshop.data.ProductCategoryRepository;
import se.lexicon.lecture_webshop.data.ProductRepository;
import se.lexicon.lecture_webshop.dto.ProductCategoryDTO;
import se.lexicon.lecture_webshop.dto.ProductDTO;
import se.lexicon.lecture_webshop.entity.Product;
import se.lexicon.lecture_webshop.entity.ProductCategory;
import se.lexicon.lecture_webshop.exception.AppResourceNotFoundException;
import se.lexicon.lecture_webshop.service.interfaces.ProductService;

import java.util.*;

import static se.lexicon.lecture_webshop.service.implementations.ProductCategoryServiceManager.COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID;

@Service
public class ProductServiceManager implements ProductService {

    public static final String COULD_NOT_FIND_PRODUCT_WITH_ID = "Could not find product with id ";
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductCategoryRepository categoryRepository;

    @Autowired
    public ProductServiceManager(ProductRepository productRepository, ModelMapper modelMapper, ProductCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProductDTO> findAll() {
        List<Product> productList = (List<Product>) productRepository.findAll();
        Collection<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : productList){
            productDTOS.add(modelMapper.map(product, ProductDTO.class));
        }
        return productDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_WITH_ID + id));


        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO form) {
        Product product = new Product();
        product.setProductName(form.getProductName());
        product.setProductPrice(form.getProductPrice());

        Set<ProductCategory> productCategories = new HashSet<>();

        if(form.getCategories() != null){
            for(ProductCategoryDTO categoryDTO : form.getCategories()){
                ProductCategory productCategory;
                if(categoryDTO.getCategoryId() != null){
                    productCategory = categoryRepository.findById(categoryDTO.getCategoryId())
                            .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID + categoryDTO.getCategoryId()));
                }else{
                    productCategory = new ProductCategory();
                    productCategory.setValue(categoryDTO.getValue());
                }
                productCategories.add(productCategory);
            }

        }

        product.setCategories(productCategories);

        Product persisted = productRepository.save(product);

        return modelMapper.map(persisted, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO update(String id, ProductDTO form) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_WITH_ID+id));

        if(form.getProductName() != null){
            product.setProductName(form.getProductName().trim());
        }

        if(form.getProductPrice() != null){
            product.setProductPrice(form.getProductPrice());
        }

        Product updated = productRepository.save(product);
        return modelMapper.map(updated, ProductDTO.class);
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProductDTO> findByCategoryName(String categoryName) {
        Collection<Product> productList = productRepository.findByCategoriesValueIgnoreCase(categoryName);
        Collection<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : productList){
            productDTOS.add(modelMapper.map(product, ProductDTO.class));
        }
        return productDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProductDTO> findByProductName(String productName) {
        Collection<Product> products = productRepository.findByName(productName);
        Collection<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            productDTOS.add(modelMapper.map(product, ProductDTO.class));
        }
        return productDTOS;
    }

    @Override
    @Transactional
    public ProductDTO addProductCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_WITH_ID+productId));

        ProductCategory productCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID+categoryId));

        product.addCategories(productCategory);

        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public ProductDTO removeProductCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_WITH_ID+productId));

        ProductCategory productCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID+categoryId));

        product.removeCategories(productCategory);

        product = productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);
    }
}
