package ru.cft.docs.keeper.service.authorization.api.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cft.docs.keeper.service.authorization.core.context.ExecutionContext;
import ru.cft.docs.keeper.service.authorization.core.service.implementation.UserAuthenticationServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ControllerFilter extends HttpFilter {

    private static final String X_AUTH_TOKEN = "X-Auth-Token";

    @Override
    public void doFilter(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
        log.info("Request received: {} {}", req.getMethod(), req.getRequestURI());
        String token = req.getHeader(X_AUTH_TOKEN);
        try {
            ExecutionContext executionContext = ExecutionContext.get();
            Long userToken = UserAuthenticationServiceImpl.verifyToken(token);
            executionContext.setUserId(userToken);
            chain.doFilter(req, res);
        } finally {
            ExecutionContext.clear();
        }
        log.info("Response sent: {}", res.getStatus());
    }
}
