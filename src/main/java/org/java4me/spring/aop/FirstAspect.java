package org.java4me.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Slf4j
@Component
@Order(1)
public class FirstAspect {

    /*
        this - class created by spring
        target - actual interface
     */
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepositoryLayer(){
    }

    /*
        @annotation - annotation above method
     */
    @Pointcut("org.java4me.spring.aop.CommonPointcuts.isControllerLayer() && " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    /*
        args - params in method
        ,.. - 0+ parameters after
        ,*,* - only n more parameters

     */
    @Pointcut("org.java4me.spring.aop.CommonPointcuts.isControllerLayer() && " +
            "args(org.springframework.ui.Model,..)")
    public void hasModelParam() {
    }
    /*
        @args - annotations above parameter's class
        .. - 0+ parameters
        ,*,* - only n more parameters
     */
    @Pointcut("org.java4me.spring.aop.CommonPointcuts.isControllerLayer() && " +
            "@args(org.java4me.spring.validation.UserInfo,..)")
    public void hasUserInfoParamAnnotation() {
    }

    /*
        bean - bean with name
     */
    @Pointcut("bean(*Service)")
    public void isServiceLayerBean() {
    }

    /*
        execution(modifiers-pattern?
                ret-type-pattern
                declaring-type-pattern?name-pattern(param-pattern)
                throws-pattern?
                )
     */
    @Pointcut("execution(public * org.java4me.spring.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Before(value = "anyFindByIdServiceMethod() && " +
            "args(id) && " +
            "target(service) && " +
            "this(serviceProxy) && " +
            "@within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
//    @Before("execution(public * org.java4me.spring.service.*Service.findById(*))")
    public void addLogging(JoinPoint joinPoint, /* Always first parameter */
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("before - invoked findById method in class {}, with id {}", service, id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod() && " +
            "target(service)", returning = "result", argNames = "result,service")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("after returning - invoked findById method in class {}, result {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod() && " +
            "target(service)", throwing = "ex")
    public void addLoggingAfterThrowing(Throwable ex, Object service) {
        log.info("after throwing - invoked findById method in class {}, exception {}: {}",
                service,
                ex.getClass(),
                ex.getMessage());
    }

    @After(value = "anyFindByIdServiceMethod() && " +
            "target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("after (finally) - invoked findById method in class {}", service);
    }


}
