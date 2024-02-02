package com.bitscoder.swoppiapp.security.services;

import com.bitscoder.swoppiapp.entities.BaseUser;
import com.bitscoder.swoppiapp.entities.Customer;
import com.bitscoder.swoppiapp.entities.Vendor;
import com.bitscoder.swoppiapp.repository.CustomerRepository;
import com.bitscoder.swoppiapp.repository.VendorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Trying to get user by email");
        Customer customer = customerRepository.findCustomerByEmail(email)
                .orElse(null);

        if (customer != null) {
            log.info("Found CUSTOMER user: {}", email);
            return UserDetailsImpl.build(customer);
        } else {
            log.info("CUSTOMER not found. Checking for VENDOR: {}", email);
        }

        Vendor vendor = vendorRepository.findVendorByEmail(email)
                .orElseThrow(() -> {
                    log.error("Vendor not found with email: {}", email);
                    return new UsernameNotFoundException("Vendor not found with email: " + email);
                });

        log.info("Found VENDOR user: {}", email);
        return buildUserDetails(vendor);
    }

    private UserDetails buildUserDetails(BaseUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user instanceof Customer) {
            authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
        } else if (user instanceof Vendor) {
            authorities.add(new SimpleGrantedAuthority("VENDOR"));
        }
        log.info("Building UserDetails for user: {}", user.getName());
        return new UserDetailsImpl(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
