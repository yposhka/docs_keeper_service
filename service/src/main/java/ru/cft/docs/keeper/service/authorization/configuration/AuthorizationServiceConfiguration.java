package ru.cft.docs.keeper.service.authorization.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        AuthorizationServiceProperties.class
})
public class AuthorizationServiceConfiguration {}
