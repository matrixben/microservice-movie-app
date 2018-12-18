package jason.luo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * aspect的作用: 日志和权限管理
 */
@Aspect
@Component
public class UserAspect {

    @Pointcut("within(jason.luo.controller.*)")
    private void controllerLog(){}

    @Before("controllerLog()")
    public void beforeHello(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Before calling "+ methodName +" method...");
    }

    @AfterReturning(pointcut = "execution(* hello(..))", returning = "resStr")
    public void bingReturnStr(String resStr){
        System.out.println("Return string is : " + resStr);
    }
}
