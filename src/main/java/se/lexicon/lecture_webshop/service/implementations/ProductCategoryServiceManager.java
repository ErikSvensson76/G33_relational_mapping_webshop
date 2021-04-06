package se.lexicon.lecture_webshop.service.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.lecture_webshop.data.ProductCategoryRepository;
import se.lexicon.lecture_webshop.dto.ProductCategoryDTO;
import se.lexicon.lecture_webshop.entity.ProductCategory;
import se.lexicon.lecture_webshop.exception.AppResourceNotFoundException;
import se.lexicon.lecture_webshop.service.interfaces.ProductCategoryService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceManager implements ProductCategoryService {

    public static final String COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID = "Could not find Product Category with id ";
    private final ProductCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductCategoryServiceManager(ProductCategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<ProductCategoryDTO> findAll() {
        List<ProductCategory> categoryList = categoryRepository.findAll();
        Collection<ProductCategoryDTO> categoryDTOS = new ArrayList<>();
        for(ProductCategory category : categoryList){
            categoryDTOS.add(modelMapper.map(category, ProductCategoryDTO.class));
        }
        return categoryDTOS;
    }

    @Override
    public ProductCategoryDTO findById(String id) {
        ProductCategory productCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID + id));
        return modelMapper.map(productCategory, ProductCategoryDTO.class);
    }

    @Override
    @Transactional
    public ProductCategoryDTO create(ProductCategoryDTO form) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setValue(form.getValue());

        ProductCategory persisted = categoryRepository.save(productCategory);

        return modelMapper.map(persisted, ProductCategoryDTO.class);
    }

    @Override
    @Transactional
    public ProductCategoryDTO update(String id, ProductCategoryDTO form) {
        ProductCategory productCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_PRODUCT_CATEGORY_WITH_ID+id));

        if(form.getValue() != null){
            productCategory.setValue(form.getValue().trim());
        }

        ProductCategory updated = categoryRepository.save(productCategory);
        return modelMapper.map(updated, ProductCategoryDTO.class);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return false;
    }
}
