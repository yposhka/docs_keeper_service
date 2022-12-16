package ru.cft.docs.keeper.service.authorization.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ru.cft.docs.keeper.service.authorization.api.logging.FilterChainExceptionHandler;

@Configuration
@Import({WebMvcConfigurationSupport.class})
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            FilterChainExceptionHandler filterChainExceptionHandler
    ) throws Exception {
        http.csrf().disable();
        return http.addFilterBefore(filterChainExceptionHandler, ChannelProcessingFilter.class).build();
    }


}
