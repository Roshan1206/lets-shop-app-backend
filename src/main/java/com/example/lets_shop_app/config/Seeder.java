package com.example.lets_shop_app.config;

import com.example.lets_shop_app.constant.Constants;
import com.example.lets_shop_app.repository.RoleRepository;
import com.example.lets_shop_app.dto.request.RegisterRequest;
import com.example.lets_shop_app.entity.Role;
import com.example.lets_shop_app.service.AuthenticationService;
import com.example.lets_shop_app.service.ProductService;
import com.example.lets_shop_app.util.FileHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Component
@Slf4j
public class Seeder {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;

    @Autowired
    private FileHandlerUtil fileHandlerUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @EventListener(ApplicationReadyEvent.class)
    public void addData() throws FileNotFoundException {
        List<String> roles = List.of(Constants.ROLE+Constants.USER, Constants.ROLE+Constants.SELLER, Constants.ROLE+Constants.ADMIN);

        roles.forEach(role -> roleRepository.findByName(role).orElseGet(
                () -> roleRepository.save(new Role(role))
        ));

        RegisterRequest request = new RegisterRequest("admin", "admin", "admin@default.com", "password");
        authenticationService.register(request, Constants.ADMIN);
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin@default.com");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        File productCategoryFile = ResourceUtils.getFile("classpath:ProductCategorySQL.sql");
        List<String> productCategories = fileHandlerUtil.extractFile(productCategoryFile);
        productService.addProductCategory(productCategories);

        File productsFile = ResourceUtils.getFile("classpath:ProductSQL.sql");
        List<String> products = fileHandlerUtil.extractFile(productsFile);
        productService.addProducts(products);

        String portNumber = environment.getProperty("local.server.port");
        log.info("All pre processing has been completed.");
        log.info("Application is now ready to use at port {}", portNumber);
    }
}
