package com.example.lets_shop_app.service.impl;

import com.example.lets_shop_app.repository.ProductCategoryRepository;
import com.example.lets_shop_app.repository.ProductRepository;
import com.example.lets_shop_app.dto.response.ProductResponse;
import com.example.lets_shop_app.dto.response.ProductSaveResponse;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.entity.ProductCategory;
import com.example.lets_shop_app.mapper.ProductMapper;
import com.example.lets_shop_app.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Service class for Product related operations
 *
 * @author Roshan
 */
@Service
public class ProductsServiceImpl implements ProductService {

    /**
     * For creating and executing queries
     */
    @PersistenceContext
    private final EntityManager entityManager;


    /**
     * Repository responsible for managing products
     */
    private final ProductRepository productRepository;


    /**
     * Repository responsible for manging product category
     */
    private final ProductCategoryRepository productCategoryRepository;


    /**
     * Injecting required dependency using constructor injection
     */
    public ProductsServiceImpl(EntityManager entityManager, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }


    /**
     * Get list of products in Page
     *
     * @param pageable contains product page related data
     * @return Product page
     */
    @Override
    public Page<ProductResponse> getProducts(Pageable pageable, String name, List<String> categories){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        Join<Product, ProductCategory> join = root.join("category", JoinType.INNER);

        applyFilters(criteriaBuilder, name, categories, root, criteriaQuery);
        applySorting(pageable, join, root, criteriaBuilder, criteriaQuery);

//        Execute query with pagination
        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<Product> products = typedQuery.getResultList();
        long totalCount = getTotalCount(criteriaBuilder, name, categories);
        List<ProductResponse> responses = products.stream().map(ProductMapper::mapToProductResponse).toList();

        return new PageImpl<>(responses, pageable, totalCount);
    }


    /**
     * Find a specific product by its id
     *
     * @param id product id
     * @return Product
     */
    @Override
    public ProductResponse getProduct(long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product not found with given id: %d", id))
        );
        return ProductMapper.mapToProductResponse(product);
    }

    /**
     * Add a new product
     *
     * @param product Product
     * @return Product id and name
     */
    @Override
    public ProductSaveResponse addProduct(Product product){
        Product newProduct = productRepository.save(product);
        return new ProductSaveResponse(newProduct.getId(), newProduct.getName());
    }


    /**
     * Add new products
     *
     * @param products Product
     * @return Product id and name
     */
    @Override
    public List<ProductSaveResponse> addAllProduct(List<Product> products){
        List<Product> productList = productRepository.saveAll(products);
        List<ProductSaveResponse> productSaveDtoList = new ArrayList<>();

        productList.forEach(product -> {
           ProductSaveResponse productSaveDto = new ProductSaveResponse(product.getId(), product.getName());
           productSaveDtoList.add(productSaveDto);
        });
        return productSaveDtoList;
    }


    /**
     * Add products from file
     * @param products List of products
     * @return Saved Product id and name
     */
    @Override
    public List<ProductSaveResponse> addProducts(List<String> products) {
        List<ProductSaveResponse> productSaveDtoList = new ArrayList<>();
        for(String string: products){
            int index = string.indexOf("VALUES");
            string = string.substring(index+8, string.length()-2).replace("'", "");
            String[] productValues = string.split(",");

            if (productValues.length > 8){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.toString(productValues) + string);
            }

            ProductCategory productCategory;
            Optional<ProductCategory> category = productCategoryRepository.findById(Long.parseLong(productValues[6]));
            productCategory = category.orElseGet(() -> createNewCategory(productValues[6]));
            Product product = ProductMapper.mapToProduct(productValues, productCategory);

            Product savedProduct = productRepository.save(product);
            ProductSaveResponse productSaveDto = new ProductSaveResponse(savedProduct.getId(), savedProduct.getName());
            productSaveDtoList.add(productSaveDto);
        }
        return productSaveDtoList;
    }


    /**
     * Create new product category
     * @param productCategories List of products
     */
    @Override
    public void addProductCategory(List<String> productCategories) {
        for (String string : productCategories){
            int index = string.indexOf("VALUES");
            string = string.substring(index+8, string.length()-2).replace("'", "");
            String[] categories = string.split(",");
            createNewCategory(categories[1]);
        }
    }


    /**
     * Create new product category
     * @param category List of products
     * @return saved product category
     */
    public ProductCategory createNewCategory(String category) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategory(category);
        return productCategoryRepository.save(productCategory);
    }


    /**
     * Get total count for creating pageable
     * @param cb criteriaBuilder
     * @param name product name
     * @param categories product categories
     * @return total product count
     */
    private long getTotalCount(CriteriaBuilder cb, String name, List<String> categories) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> productRoot = countQuery.from(Product.class);
        Join<Product, ProductCategory> categoryJoin = productRoot.join("category", JoinType.INNER);

        countQuery.select(cb.count(productRoot));

        applyFilters(cb, name, categories, productRoot, countQuery);

        return entityManager.createQuery(countQuery).getSingleResult();
    }


    /**
     * Builds and applies WHERE clause predicates for filtering.
     * Used by both main query and count query to ensure consistency.
     */
    private void applyFilters(CriteriaBuilder cb, String name, List<String> categories, Root<Product> productRoot, CriteriaQuery<?> countQuery) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(productRoot.get("name")),
                    "%" + name.toLowerCase() + "%"));
        }

        if (categories != null && !categories.isEmpty()) {
            predicates.add(productRoot.get("category").get("productCategory").in(categories));
        }

        if (!predicates.isEmpty()) {
            countQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        }
    }


    /**
     * Applies ORDER BY clause based on Pageable sort configuration.
     * Handles both product properties and joined category properties.
     */
    private void applySorting(Pageable pageable, Join<Product, ProductCategory> join, Root<Product> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<Product> criteriaQuery) {
        // Apply sorting - ORDER BY clause
        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();

            // Spring's Sort can contain multiple sort orders
            // e.g., Sort.by("category").ascending().and(Sort.by("price").descending())
            pageable.getSort().forEach(sortOrder -> {
                String property = sortOrder.getProperty(); // Field name to sort by
                Path<?> sortPath;

                // Handle different entity properties:
                if ("category".equals(property)) {
                    // Special case: sorting by category name (joined entity)
                    // SQL: ORDER BY product_category.name
                    sortPath = join.get("name");
                } else {
                    // Regular product properties: id, name, price, stock, etc.
                    // SQL: ORDER BY product.name, product.price, etc.
                    sortPath = root.get(property);
                }

                // Create ascending or descending order
                if (sortOrder.isAscending()) {
                    orders.add(criteriaBuilder.asc(sortPath));  // ASC
                } else {
                    orders.add(criteriaBuilder.desc(sortPath)); // DESC
                }
            });

            // Apply all ORDER BY clauses to the query
            // SQL: ORDER BY category ASC, price DESC (example)
            criteriaQuery.orderBy(orders);
        }
    }
}
