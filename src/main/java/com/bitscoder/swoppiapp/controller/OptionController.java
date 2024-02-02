package com.bitscoder.swoppiapp.controller;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.OptionDto;
import com.bitscoder.swoppiapp.service.OptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("swoppiApp/v1/option")
public class OptionController {

    private OptionService optionService;

    @PostMapping("/add-option")
    public ResponseEntity<ApiResponse<OptionDto.Response>> createOption(Principal principal, @RequestBody String optionDtoString) {
        return optionService.createOption(principal, optionDtoString);
    }
}
