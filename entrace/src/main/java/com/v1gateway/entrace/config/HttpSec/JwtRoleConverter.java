package com.v1gateway.entrace.config.HttpSec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Return Type Collection<GrantedAuthority>
public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        // Null pointer exception  safe check
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return new ArrayList<>();
        }

        List<String> roles = (List<String>) realmAccess.get("roles");

        // return  SimpleGrantedAuthority as  GrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}