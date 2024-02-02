package com.bitscoder.swoppiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private String description;
    private List<String> ingredients;
    private String categoryId;
    private double defaultPrice;
    private double salesPrice;
    private String productImageUrl;
    private List<String> optionIds;
    private String vendorId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{

        private String productId;
        private String productName;
        private String description;
        private List<String> ingredients;
        private String categoryId;
        private double defaultPrice;
        private double salesPrice;
        private String productImageUrl;
        private List<String> optionIds;
        private String vendorId;
    }
}
