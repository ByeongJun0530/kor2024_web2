package example.상속과구현;

import lombok.val;

class Car{
    Tire tire; // Tire 타입으로 멤버변수 생성
    public void run(){ this.tire.roll();} // Tire 타입의 roll 메소드 사용/호출
}

class Tire{
    public void roll(){
        System.out.println("일반 타이어가 회전합니다.");
    }
}

class KumhoTire extends Tire{ // 금호타이어는 일반 타이어에게 상속받는다.
    @Override // extends 키워드 tire 타입에게 물려받은 메소드를 재정의 : 오버라이딩
    public void roll(){
        System.out.println("금호타이어가 회전합니다.");
    }
}
class HankookTire extends Tire{
    @Override
    public void roll(){
        System.out.println("한국 타이어가 회전합니다.");
    }
}

public class 상속예제 {
    public static void main(String[] args) {
        // [1]
        Car myCar = new Car();
        // myCar.run();    // nullPointerException
        // 즉] Car 객체는 존재하지만 Car 객체 내 tire 객체가 존재하지 않으므로 오류가 발생
        // myCar.run() --> tire.roll() 할 수 없다.

        // [2] 일반 타이어가 회전합니다.
        Car yourCar = new Car();
        yourCar.tire = new Tire();
        yourCar.run(); // yourCar 객체 내 Tire 객체를 대입 했으므로
        // 즉] Car 객체는 존재하고 Car 객체 내 tire 객체가 존재하므로 실행 가능.

        // [3] 일반 타이어가 회전합니다.
        myCar.tire = new Tire(); // myCar 객체 내 Tire 객체를 대입 했으므로
        myCar.run();
        // 즉] Car 객체는 존재하고 Car 객체 내 tire 객체가 존재하므로 실행 가능

        // [4] 금호타이어가 회전합니다.
        myCar.tire = new KumhoTire();
        myCar.run(); // myCar 객체 내 KumhoTire 객체를 대입했으므로
        // 즉] Car 객체는 존재하고 Car 객체 내 KumhoTire 객체가 존재하므로 실행 가능

        // [5] 한국 타이어가 회전합니다.
        myCar.tire = new HankookTire();
        myCar.run();    // myCar 객체 내 HankookTire 객체를 대입 했으므로
        // 즉] Car  객체는 존재하고 Car 객체 내 HankookTire 객체가 존재하므로 오류가 없다.

        // [5]
        yourCar.run(); // yourCar 객체는 tire 변경(대입)하지 않으므로 그대로 Tire 객체가 존재한다.

        // 즉]
        // 1. 일반 메소드 호출을 하기 위해서는 인스턴스(new) 필요하다.
        // 2. Tire 타입에는 KumhoTire, HankookTire 가 대입된다. 다형성 특징
    }

}





















