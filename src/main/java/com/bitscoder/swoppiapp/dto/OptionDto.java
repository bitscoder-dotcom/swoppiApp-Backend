package com.bitscoder.swoppiapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {

    private String optionName;
    private boolean isSingleChoice;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String optionId;
        private String optionName;
        private boolean isSingleChoice;
    }
}
