package org.java4me.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {

    /*
        @within - annotation above class
     */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
        within - class by name
        .* - all subclasses
        ..* - all nested subclasses
     */
    @Pointcut("within(org.java4me.spring.service.*Service)")
    public void isServiceLayer() {
    }
}
