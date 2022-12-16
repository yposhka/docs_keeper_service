package ru.cft.docs.keeper.service.authorization.api.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.cft.docs.keeper.service.authorization.api.logging.ControllerFilter;

import javax.servlet.Filter;

@Configuration
public class HttpConfiguration {
    @Bean
    public FilterRegistrationBean<Filter> requestFilterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ControllerFilter());
        registrationBean.setAsyncSupported(true);
        registrationBean.addUrlPatterns("/documents/*");
        return registrationBean;
    }
}
