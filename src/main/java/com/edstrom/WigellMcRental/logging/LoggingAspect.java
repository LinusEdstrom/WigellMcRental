package com.edstrom.WigellMcRental.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //Dto:et är ett object så namnet spelar ingen roll så kör på bara dto för att det ska vara konsistent and what not

    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PostMapping) && args(dto,..)")
    public void logCreate(JoinPoint joinPoint, Object dto) {
        logger.info("CREATE called in {} with: {}",
                joinPoint.getSignature().getDeclaringTypeName(), dto);
    }
    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PutMapping) && args(dto,..)")
    public void logUpdate(JoinPoint joinPoint, Object dto) {
        logger.info("UPDATE called in {} with: {}",
                joinPoint.getSignature().getDeclaringTypeName(), dto);
    }

    @AfterReturning("@annotation(org.springframework.web.bind.annotation.PatchMapping) && args(dto,..)")
    public void logPatch(JoinPoint joinPoint, Object dto) {
        logger.info("PATCH called in {} with: {}",
                joinPoint.getSignature().getDeclaringTypeName(), dto);
    }
    @AfterReturning("@annotation(org.springframework.web.bind.annotation.DeleteMapping) && args(id,..)")
    public void logDelete(JoinPoint joinPoint, Object id) {
        logger.info("DELETE called in {} with id: {}",
                joinPoint.getSignature().getDeclaringTypeName(), id);
    }

}
