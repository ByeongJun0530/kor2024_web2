package korweb.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Vector;

@Component
public class LoginServerSocket extends TextWebSocketHandler {

    protected  static final List<WebSocketSession> 접속명단 = new Vector<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("클라이언트 서버 접속 성공");
        접속명단.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("클라이언트 서버 접속 해제");
        접속명단.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("받은내용 : " + message.getPayload() ); // .getPayload() : 받은 메시지 객체내 본문 반환 함수.
        System.out.println("보낸클라이언트의 소켓정보 : " + session ); // 서버에게 메시지를 보낸 클라이언트 소켓 정보
        // *클라이언트에게 메시지를 보내기. , 받을클라이언트소켓.send( new TextMessage("보낼 내용") )
        // session.sendMessage( new TextMessage("안녕 클라이언트") ); // 메시지를 보낸 클라이언트에게 서버가 메시지를 보내기.

        // [3] 서버소켓이 특정 클라이언트 소켓으로 부터 받은 메시지를 접속된 모든 클라이언트소켓들에게 보내기
        for( int index = 0 ; index <= 접속명단.size() - 1 ; index++ ){
            WebSocketSession 접속된클라이언트 = 접속명단.get( index ); // index번째 클라이언트소켓 꺼내기
            접속된클라이언트.sendMessage( message ); // 서버소켓이 받은 message를 접속된 모든 소켓들에게 메시지 전송하기.
        }
    }
}
