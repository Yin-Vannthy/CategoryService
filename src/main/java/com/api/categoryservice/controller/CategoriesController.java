package com.api.categoryservice.controller;

import com.api.categoryservice.model.request.CategoryRequest;
import com.api.categoryservice.service.CategoriesService;
import com.api.categoryservice.util.APIResponseUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
//@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class CategoriesController {
    private CategoriesService categoriesService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(categoriesService.getAllCategories(), HttpStatus.OK)
        );
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<?> getAllCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(categoriesService.getCategoryById(categoryId), HttpStatus.OK)
        );
    }

    @PostMapping("/addNewCategory")
    public ResponseEntity<?> addNewCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(categoriesService.addNewCategory(categoryRequest), HttpStatus.CREATED)
        );
    }

    @PutMapping("/updateCategoryById/{categoryId}")
    public ResponseEntity<?> updateCategoryById(@PathVariable Integer categoryId, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(categoriesService.updateCategoryById(categoryId, categoryRequest), HttpStatus.OK)
        );
    }

    @DeleteMapping("/deleteCategoryById/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(categoriesService.deleteCategoryId(categoryId), HttpStatus.OK)
        );
    }
}
