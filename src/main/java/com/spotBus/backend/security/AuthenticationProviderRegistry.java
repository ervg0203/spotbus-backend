package com.spotBus.backend.security;

import com.spotBus.backend.exception.UnsupportedUserTypeException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthenticationProviderRegistry {

    private final Map<UserType, UserAuthenticationProvider> providers;

    public AuthenticationProviderRegistry(List<UserAuthenticationProvider> providerList) {
        this.providers = providerList.stream()
                .collect(Collectors.toMap(UserAuthenticationProvider::getUserType, Function.identity()));
    }

    public UserAuthenticationProvider getProvider(UserType userType) {
        UserAuthenticationProvider provider = providers.get(userType);
        if (provider == null) {
            throw new UnsupportedUserTypeException("Authentication is not supported for user type: " + userType);
        }
        return provider;
    }
}
