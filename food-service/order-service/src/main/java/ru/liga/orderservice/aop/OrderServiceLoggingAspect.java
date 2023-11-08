package ru.liga.orderservice.aop;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
public class OrderServiceLoggingAspect {
    private final Logger logger = LogManager.getLogger(OrderServiceLoggingAspect.class);

    @Pointcut("execution(* ru.liga.orderservice.services.OrderService.updateOrderStatus(..))")
    public void updateOrderStatusMethod() {
    }

    @After("updateOrderStatusMethod()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();

        logger.log(Level.INFO, new Date() + " Вызывается метод изменения статуса заказа: "
                +jp.getSignature().getName() +
                " На вход поступают данные: "+ Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "updateOrderStatusMethod()", returning = "result")
    public void logAfterReturning(JoinPoint jp, Object result) {

        logger.info(new Date() + " Метод изменения статуса заказа завершился успешно и " +
                "вернул " + result.toString());
    }

    @AfterThrowing(pointcut = "updateOrderStatusMethod()", throwing = "exception")
    public void logExceptionEnding(JoinPoint jp, Throwable exception) {
        logger.info( new Date() + " Метод изменения статуса заказа завершился неудачно, вызвав ошибку " +
                exception.getClass().getName() + " с сообщением " + exception.getMessage());
    }


}
