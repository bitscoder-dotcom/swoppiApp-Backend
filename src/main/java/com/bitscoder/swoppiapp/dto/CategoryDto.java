package com.bitscoder.swoppiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryName;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String categoryId;
        private String categoryName;
    }
}
