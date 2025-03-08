package korweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ====== 템플릿 반환하는 컨트롤러 클래스 ====== //
@Controller
public class ViewController {

    // [1] 메인페이지를 반환해주는 메소드
    @GetMapping("") // http://localhost:8080/
    public String index(){return "index.html";}

    // [2] 로그인 페이지를 반환해주는 메서드
    @GetMapping("/member/login")
    public String login(){return "/member/login.html";}

    // [3] 회원가입 페이지를 반환해주는 메서드
    @GetMapping("/member/signup")
    public String signup(){return "/member/signup.html";}

    // [4] 마이페이지 반환해주는 메소드
    @GetMapping("/member/info")
    public String info(){return "/member/info.html";}

    // [5] 수정페이지 반환해주는 메소드
    @GetMapping("/member/update")
    public String myUpdate(){return "/member/update.html";}

    // [6] 게시물 목록 페이지를 반환 해주는 메소드
    @GetMapping("/board")
    public String board(){return "/board/board.html";}

    // [7] 게시물 쓰기 페이지 반환 해주는 메소드
    @GetMapping("/board/write")
    public String boardWrite(){ return "/board/write.html";}

    // [8] 게시물 개별 조회 페이지 반환해주는 메소드
    @GetMapping("/board/view")
    public String boardView(){return "/board/view.html";}

    // [9] 공공데이터 API 페이지 반환해주는 메소드
    @GetMapping("/api1")
    public String api1(){return "/api/api1.html";}

    @GetMapping("/api2")
    public String api2(){return "/api/api2.html";}

    @GetMapping("/chat")
    public String chat(){return "/api/chat.html";}
}
