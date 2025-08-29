package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.dao.ProductCategoryRepository;
import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.dto.ProductDto;
import com.example.lets_shop_app.dto.ProductSaveDto;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.entity.ProductCategory;
import com.example.lets_shop_app.mapper.ProductMapper;
import com.example.lets_shop_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service class for Product related operations
 *
 * @author Roshan
 */
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductService {


    /**
     * Injecting {@link ProductRepository} for DB operations
     */
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;


    /**
     * Get list of products in Page
     *
     * @param pageable contains product page related data
     * @return Product page
     */
    @Override
    public Page<ProductDto> getProducts(Pageable pageable, String name, String category){
        Page<Product> products = null;
        if(!category.isEmpty() && !name.isEmpty()){
            ProductCategory productCategory = productCategoryRepository.findByProductCategory(category);
            products = productRepository.findByCategoryAndNameContaining(productCategory.getId(), name, pageable);
        }
        if(!name.isEmpty()){
            products = productRepository.findByNameContaining(name, pageable);
        }
        if (!category.isEmpty()){
            ProductCategory productCategory = productCategoryRepository.findByProductCategory(category);
            products = productRepository.findByCategory(productCategory.getId(), pageable);
        }
        if(products==null){
            products = productRepository.findAll(pageable);
        }
        return products.map(ProductMapper::mapToProductDto);
    }


    /**
     * Find a specific product by its id
     *
     * @param id product id
     * @return Product
     */
    @Override
    public ProductDto getProduct(long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product not found with given id: %d", id))
        );
        return ProductMapper.mapToProductDto(product);
    }

    /**
     * Add a new product
     *
     * @param product Product
     * @return Product id and name
     */
    @Override
    public ProductSaveDto addProduct(Product product){
        Product newProduct = productRepository.save(product);
        return new ProductSaveDto(newProduct.getId(), newProduct.getName());
    }


    /**
     * Add new products
     *
     * @param products Product
     * @return Product id and name
     */
    @Override
    public List<ProductSaveDto> addAllProduct(List<Product> products){
        List<Product> productList = productRepository.saveAll(products);
        List<ProductSaveDto> productSaveDtoList = new ArrayList<>();

        productList.forEach(product -> {
           ProductSaveDto productSaveDto = new ProductSaveDto(product.getId(), product.getName());
           productSaveDtoList.add(productSaveDto);
        });
        return productSaveDtoList;
    }

    @Override
    public List<ProductSaveDto> addProducts(List<String> products) {
        List<ProductSaveDto> productSaveDtoList = new ArrayList<>();
        for(String string: products){
            int index = string.indexOf("VALUES");
            string = string.substring(index+8, string.length()-2).replace("'", "");
            String[] productValues = string.split(",");

            if (productValues.length > 8){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, productValues.toString() + string);
            }

            ProductCategory productCategory = new ProductCategory();
            Optional<ProductCategory> category = productCategoryRepository.findById(Long.parseLong(productValues[6]));
            productCategory = category.isPresent() ? category.get() : createNewCategory(productValues[6]);
            Product product = ProductMapper.mapToProduct(productValues, productCategory);

            Product savedProduct = productRepository.save(product);
            ProductSaveDto productSaveDto = new ProductSaveDto(savedProduct.getId(), savedProduct.getName());
            productSaveDtoList.add(productSaveDto);
        }
        return productSaveDtoList;
    }

    @Override
    public void addProductCategory(List<String> productCategories) {
        for (String string : productCategories){
            int index = string.indexOf("VALUES");
            string = string.substring(index+8, string.length()-2).replace("'", "");
            String[] categories = string.split(",");
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategory(categories[1]);
            productCategoryRepository.save(productCategory);
        }
    }


    public ProductCategory createNewCategory(String category){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategory(category);
        return productCategoryRepository.save(productCategory);
    }
}
