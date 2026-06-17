package com.codexdei.springboot.app.crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* import com.codexdei.springboot.app.crud.ProductValidation;
 */import com.codexdei.springboot.app.crud.entities.Product;
import com.codexdei.springboot.app.crud.services.ProductService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

/*     @Autowired
    private ProductValidation validation; */

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Product> list() {

        return productService.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> view(@PathVariable Long id) {

        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {

            return ResponseEntity.ok(productOptional.get());

        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {

    System.out.println("AUTH = " + SecurityContextHolder.getContext().getAuthentication());
    System.out.println("ROLES = " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

/*         validation.validate(product, result);
 */
        if (result.hasFieldErrors()) {

            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result,
            @PathVariable Long id) {

/*         validation.validate(product, result);
 */
        if (result.hasFieldErrors()) {

            return validation(result);
        }

        Optional<Product> producOptional = productService.update(id, product);

        if (producOptional.isPresent()) {

            return ResponseEntity.status(HttpStatus.CREATED).body(producOptional.orElseThrow());

        } else {

            return ResponseEntity.notFound().build();
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {

        Optional<Product> productOptional = productService.delete(id);

        if (productOptional.isPresent()) {

            return ResponseEntity.ok(productOptional.orElseThrow());

        } else {

            return ResponseEntity.notFound().build();
        }

    }

    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err ->

        errors.put(err.getField(), "Field '" + err.getField() + "' " + err.getDefaultMessage())

        );

        return ResponseEntity.badRequest().body(errors);
    }

}
