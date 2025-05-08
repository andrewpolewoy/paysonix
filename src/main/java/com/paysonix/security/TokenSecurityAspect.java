package com.paysonix.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

/**
 * Аспект безопасности для проверки токена в заголовке запроса.
 * Применяется к методам, помеченным аннотацией @RequiresToken.
 */
@Aspect
@Component
public class TokenSecurityAspect {
    private static final Logger logger = LoggerFactory.getLogger(TokenSecurityAspect.class);

    @Value("${app.security.token}")
    private String expectedToken;

    /**
     * Проверяет наличие и корректность токена в заголовке запроса.
     * Если токен отсутствует или неверный, выбрасывает исключение ResponseStatusException.
     */
    @Around("@annotation(com.paysonix.security.RequiresToken)")
    public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Token");
        
        logger.info("TokenSecurityAspect: checking token for method {}", joinPoint.getSignature().getName());
        logger.debug("TokenSecurityAspect: expectedToken='{}', receivedToken='{}'", expectedToken, token);
        
        if (token == null || !token.equals(expectedToken)) {
            logger.warn("Token validation failed! Expected: '{}', Received: '{}'", expectedToken, token);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
        }
        
        logger.info("Token validation successful!");
        return joinPoint.proceed();
    }
} 