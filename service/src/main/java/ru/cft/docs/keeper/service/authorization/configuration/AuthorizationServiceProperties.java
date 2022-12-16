package ru.cft.docs.keeper.service.authorization.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "authorization-service")
@Getter
@Setter
public class AuthorizationServiceProperties {
    @NotNull
    private Duration userTokenLifetime;
}
