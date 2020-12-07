package ru.teamidea.odatapractice.products.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.annotation.JsonProperty;

import ru.teamidea.odatapractice.products.models.Product;

/*
 * Use a path which does not end with a slash! Otherwise the controller is not reachable when not using the trailing
 * slash in the URL
 */
@RestController
@RequestMapping(path = ProductsController.PATH)
@RequestScope // @Scope(WebApplicationContext.SCOPE_REQUEST)
@Validated
public class ProductsController {
    public static final String PATH = "/odata";
    private static int ID = 0;

    private static final Map<Long, Product> prods = new HashMap<>();

    @GetMapping
    public ProductList products() {
        return new ProductList(prods.values());
    }

   
    public static class ProductList {
        @JsonProperty("value")
        public List<Product> products = new ArrayList<>();

        public ProductList(Iterable<Product> prods) {
            prods.forEach(products::add);
        }
    }
}