package korweb.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Aspect // AOP 설정하는 어노테이션 주입
@Component // 스프링 컨테이너에 빈 등록
public class LogCheck {
    // [1]
    // + StudentService 클래스 내 모든 메소드가 실행 되기 전에 자동으로 부가 기능 실행 + 실행된 함수의 인수
    // 첫번째 : 모든 반환 타입의 메소드
    // korweb.service.StudentService : 클래스가 위치한 경로를 ( src -> main -> java )
    // 두 번째 .* : 앞에 있는 클래스 내 모든 메소드 뜻
    // (..) : 메소드들의 매개변수 타입 뜻, (..) : 모든 타입 뜻
    /*
    @Before("execution(* korweb.service.StudentService.*(..)) && args ( param )") // args : argument : 인수
    public void logBefore( Object param){ // Object : 여러 메소드들의 인수값 타입을 대입받기 위해서는 슈퍼클래스인 Object 사용
        System.out.println("[AOP] studentService 발동");
        System.out.println("[AOP] 매개변수 : " + param);
    }

    // [2] @AfterReturning
        // + StudentService 클래스 내 모든 메소드가 *정상 실행 종료* 되었을 때 자동으로 부가 기능 실행 + 실행된 메소드의 리턴값
    @AfterReturning(value = "execution( * korweb.service.StudentService.*(..))", returning = "result")
    public void logReturn(Object result){
        System.out.println("[AOP] StudentService 종료");
        System.out.println("[AOP] 반환값 : " + result);
    }
     */

    // [3] @Around :
    @Around("execution( * korweb.service.StudentService.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        // AOP 제공하는 인터페이스 : ProceedingJoinPoint
        System.out.println(joinPoint.getArgs()); // 발동된 메서드의 인수 반환/가져오기 [ 배열타입 반환 : 인수가 여러개 있을 수 있으므로 ]
        System.out.println(joinPoint.getSignature()); // 발동된 메소드의 선언부(반환타입 함수명 매개변수 정보) 반환

        // (1) 발동된 메소드의 함수명
        System.out.println("[AOP] 현재 실행된 서비스명 : " + joinPoint.getSignature());
        // (2) 발동된 메소드의 인수, Arrays.toString(배열타입변수명) : 배열 내 값들을 문자열로 반환( vs DTO 의 toString() 같은 의미)
        System.out.println("[AOP] 현재 실행된 서비스의 인수 : " + Arrays.toString(joinPoint.getArgs()));
        // (3) 메소드 발동하고 리턴값 받기, .proceed() : 현재 발동한 메소드를 직접 실행, + 예외처리 필수
        Object result = joinPoint.proceed();
        // (4) 메소드 실행 후 리턴값 받기
        System.out.println("[AOP] 현재 실행된 서비스의 반환 : " + result);
        // + 발동한 메소드의 결과값을 그래도 리턴한다.
        return result;
    }


}
