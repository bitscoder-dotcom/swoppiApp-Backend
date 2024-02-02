package com.bitscoder.swoppiapp.service;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.OptionDto;
import com.bitscoder.swoppiapp.entities.Option;
import com.bitscoder.swoppiapp.entities.Vendor;
import com.bitscoder.swoppiapp.repository.OptionRepository;
import com.bitscoder.swoppiapp.repository.VendorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class OptionService {

    private OptionRepository optionRepository;
    private VendorRepository vendorRepository;

    public ResponseEntity<ApiResponse<OptionDto.Response>> createOption(Principal principal, String optionDtoString) {

        try {

            String email = principal.getName();
            Vendor vendor = vendorRepository.findVendorByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));

            ObjectMapper objectMapper = new ObjectMapper();
            OptionDto OptionDto = objectMapper.readValue(optionDtoString, OptionDto.class);

            Option option = convertDtoToEntity(OptionDto);
            option.setVendor(vendor);

            optionRepository.save(option);

            OptionDto.Response savedOptionDto = convertEntityToDto(option);

            ApiResponse<OptionDto.Response> apiResponse = new ApiResponse<>(
                    LocalDateTime.now(),
                    UUID.randomUUID().toString(),
                    true,
                    "Option created successfully",
                    savedOptionDto
            );

            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error creating Option", e);
            throw new RuntimeException("Error creating Option", e);
        }
    }

    private Option convertDtoToEntity(OptionDto optionDto) {
        Option option = new Option();
        option.setOptionName(optionDto.getOptionName());
        option.setSingleChoice(optionDto.isSingleChoice());
        return option;
    }

    private OptionDto.Response convertEntityToDto(Option option) {
        OptionDto.Response optionResponse = new OptionDto.Response();
        optionResponse.setOptionId(option.getOptionId());
        optionResponse.setOptionName(option.getOptionName());
        optionResponse.setSingleChoice(option.isSingleChoice());
        return optionResponse;
    }
}
