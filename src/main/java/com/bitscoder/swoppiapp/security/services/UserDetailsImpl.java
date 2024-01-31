package com.bitscoder.swoppiapp.security.services;

import com.bitscoder.swoppiapp.entities.BaseUser;
import com.bitscoder.swoppiapp.entities.Customer;
import com.bitscoder.swoppiapp.entities.Vendor;
import com.bitscoder.swoppiapp.enums.UserTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String userId;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            String userId, String username, String email, String password,
            List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.name = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;

        logUserDetailsCreation();
    }
    private void logUserDetailsCreation() {
        log.info("UserDetails created for user: {}", name);
        log.debug("UserDetails: {}", this);
    }

    public static UserDetailsImpl build(BaseUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user instanceof Customer) {
            authorities.add(new SimpleGrantedAuthority(UserTypes.CUSTOMER.name()));
        } else if (user instanceof Vendor) {
            authorities.add(new SimpleGrantedAuthority(UserTypes.VENDOR.name()));
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
        log.info("UserDetailsImpl built for user: {}", userDetails.getUsername());
        log.debug("UserDetailsImpl: {}", userDetails);

        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "id='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
