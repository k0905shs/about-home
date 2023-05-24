package com.homeApi.config;

import com.core.config.core.Trace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Log Trace
 */
@Slf4j
@Aspect
@Component
public class LoggingAdvice {
    private final ThreadLocal<Trace> traceLocal = new ThreadLocal<>();

    @Around("execution(* com.homeApi..*Service.*(..)) || execution(* com.homeApi..*Repository*.*(..))" +
            " || execution(* com.homeApi..*Controller.*(..)) || execution(* com.homeApi..*GovApi.*(..))")
    public Object doTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        String location = joinPoint.getSignature().toString();  //AOP 실행 위치 정보
        Exception exceptionHolder = null; //예외 발생시 예외를 로직 실행 이 후에 throw 해주기 위한 변수

        if(traceLocal.get() == null)
            traceLocal.set(new Trace(UUID.randomUUID().toString().substring(0,8),1));

        try {
            trace(location,traceLocal.get(), joinPoint, exceptionHolder); //호출 로그 (1 출력)
            traceLocal.get().nextLevel(); // 다음 레벨 로그 출력을 위한 위한 레벨 업
            return joinPoint.proceed(); //프록시 내부 로직 실행
        } catch (Exception e) {
            exceptionHolder = e;//예외 hold
        }finally {
            traceLocal.get().prevLevel();//레벨 증가 하기 전와 같은 레벨로 돌리기 위해 레벨 마이너스
            trace(location,traceLocal.get(), joinPoint, exceptionHolder); // 반환 출력
            if(traceLocal.get().isLevelOne())//만약 레벨이 1이면 쓰레드 로컬 변수 제거
                traceLocal.remove();
        }
        throw exceptionHolder; //예외 throw
    }

    /**
     * 메소드 실행 추적
     */
    private void trace(String location,Trace trace, ProceedingJoinPoint joinPoint, Exception e) throws IllegalAccessException {
        int level = trace.getLevel();
        String fields = checkFields(joinPoint);
        if(e == null)
            log.info("[{}] [level{}], location : {}, fields : [{}]", trace.getTraceId(), level, location, fields);
        else
            log.info("[{}] [level{}], location : {}, fields : [{}], Exception : {}", trace.getTraceId(), location, level, fields, e.toString());
    }

    /**
     * 해당 메소드 dto 필드값 확인
     */
    private String checkFields(ProceedingJoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            for (Field field : arg.getClass().getDeclaredFields()) { //dto 필드 확인
                field.setAccessible(true); //필드 접근 허용
                sb.append(field.getName()).append("=").append(field.get(arg)).append(", ");
            }
        }
        return sb.toString();
    }
}
