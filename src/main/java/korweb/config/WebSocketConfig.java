package korweb.config;

import korweb.controller.ChatServerSocket;
import korweb.controller.LoginServerSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration // 해당 클래스를 스프링 컨테이너 빈(객체) 등록
@EnableWebSocket // 웹 소켓를 스프링 컨테이너 빈(객체) 등록
public class WebSocketConfig implements WebSocketConfigurer {

    // [*] 서버소켓 역할을 하는 컨트롤러 클래스/객체 생성
    @Autowired private ChatServerSocket chatServerSocket;
    @Autowired private LoginServerSocket loginServerSocket;

    // [*] WebSocketConfigurer 인터페이스의 추상메소드로 웹소켓 주소를 매핑하는 함수
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //registry.addHandler( 매핑할클래스변수명 , "서버소켓주소정의");
        registry.addHandler(  chatServerSocket, "/socket/server"); // HTTP 의 매핑 주소와 중복이 불가능.
        registry.addHandler( loginServerSocket, "/socket/login");
    } // f end


} // c end
