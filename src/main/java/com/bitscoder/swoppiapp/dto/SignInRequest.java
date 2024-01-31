package com.bitscoder.swoppiapp.dto;

import com.bitscoder.swoppiapp.enums.UserTypes;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class SignInRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response {
        private String userId;
        private String token;
        private String type = "Bearer";
        private String name;
        private Set<UserTypes> userTypes;
        private LocalDateTime tokenExpirationDate;

        public Response(String userId, String token, String name, Set<UserTypes> userTypes, LocalDateTime tokenExpirationDate) {
            this.userId = userId;
            this.token = token;
            this.name = name;
            this.userTypes = userTypes;
            this.tokenExpirationDate = tokenExpirationDate;
        }
    }
}
