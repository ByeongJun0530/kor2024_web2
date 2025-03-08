package korweb.controller;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// (1) 인스턴스 생성하여 메소드를 호출한다.
//public class TaskController {
//    public void method1(String[] args) {
//        TaskService taskService = new TaskService();
//        taskService.method1();
//    }
//}

// (2) 인스턴스 생성하여 메소드를 호출한다
//public class TaskController {
//    public void method1(){
//        new TaskService().method1();
//    }
//}

// (3) 싱글톤
//public class TaskController {
//    public void method1(){
//        TaskService.getInstance().method1();
//    }
//}

// (4) 메소드가 static 일 때
//public class TaskController {
//    public void method1() {
//        TaskService.method1();
//    }
//}

// (5) IOC, DI 활용한 다른 클래스의 인스턴스 메소드 호출/사용
//public class TaskController {
//    @Autowired private TaskService taskService;
//    public void method1(){
//        taskService.method1();
//    }
//}

// ================== [1] AOP 없이 구현된 코드 ==================
class TaskService2{
    // 메소드1
    public void enter1(){
        System.out.println("(보안) 코로나 열 체크"); // (1) 부가 기능
        System.out.println("식당 입장");           // (2) 비지니스 로직 -> 서비스를 제공함에 있어서 핵심이 되는 로직
    }
    // 메소드2
    public void enter2(){
        System.out.println("(보안) 코로나 열 체크"); // (1) 부가 기능
        System.out.println("학원 입장");           // (2) 비지니스 로직
    }
}

public class TaskController {
    public static void main(String[] args) {
        TaskService2 taskService2 = new TaskService2();
        taskService2.enter1(); // (보안) 코로나 열 체크, 식당 입장
        taskService2.enter2(); // (보안) 코로나 열 체크, 학원 입장
        // 단점 : 유지보수 복잡하다. 하나의 메소드에서 부가기능이 변경되면 다른 메소드도 같이 수정해야한다.
        // 예] 국가에서 식당과 학원에 입장하기 위해 보안을 열 체크와 자가진단 -> 보안 기능별로 따로 분해해서 관리하자. AOP

    }
}

// ================== [2] AOP 활용하여 구현된 코드 ==================
@SpringBootApplication
class TaskStart3{
    public static void main(String[] args) {
        SpringApplication.run(TaskStart3.class, args);
    }
}

@RestController
class TaskController3{
    @Autowired TaskService3 taskService3;

    @GetMapping("/aop")
    public void aop(){
        taskService3.enter1();
    }
}

@Service
class TaskService3{
    public void enter1(){
        // System.out.println("(보안) 코로나 열 체크");
        System.out.println("식당 입장");
    }
    public void enter2(){
        // System.out.println("(보안) 코로나 열 체크");
        System.out.println("학원 입장");
    }
}

@Aspect // AOP 설정하는 클래스
@Component // 빈 등록
class TaskSecurity{
    @Before("execution(*TaskService3.*(..))")
    public void securityCheck(){
        System.out.println("(보안) 코로나 열 체크");
    }
}











