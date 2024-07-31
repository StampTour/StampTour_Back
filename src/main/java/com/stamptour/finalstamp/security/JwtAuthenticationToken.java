package com.stamptour.finalstamp.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Setter
@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final String principal;

    public JwtAuthenticationToken(String principal, String token) {
        super(null);
        this.token = token;
        this.principal = principal;
        setAuthenticated(false);
    }


    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
