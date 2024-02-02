package com.bitscoder.swoppiapp.controller;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.CategoryDto;
import com.bitscoder.swoppiapp.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("swoppiApp/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse<CategoryDto.Response>> createCategory(Principal principal, @RequestBody String categoryDtoString) {
        return categoryService.createCategory(principal, categoryDtoString);
    }
}
