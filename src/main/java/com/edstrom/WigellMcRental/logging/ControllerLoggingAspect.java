/*package com.edstrom.WigellMcRental.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ControllerLoggingAspect.class);


    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void logPost(JoinPoint joinPoint) {
        log.info("POST called: {} with args: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void logPut(JoinPoint joinPoint) {
        log.info("PUT called: {} with args: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void logDelete(JoinPoint joinPoint) {
        log.warn("DELETE called: {} with args: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }
}

 */
