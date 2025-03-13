package korweb.config;

import korweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 컨테이너 빈 등록
public class SecurityConfig {

    // [1] 시큐리티의 필터 정의하기
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        // [3] HTTP 요청에 따른 부여된 권한/상태 확인 후 자원 허가 제한
        httpSecurity.authorizeHttpRequests((http) -> {
                    // 3-1 : 모든 http 요청에 "/**"(모든 url 뜻).antMatcher('http 경로').permitAll() : 지정한 경로에는 누구나 접속할 수 있다.
                    // 3-2 : 글쓰기 페이지(board/write)에는 로그인된 회원만 접근할 수 있다.
                    // 3-3 : 채팅페이지(chat)에는 로그인 회원이면서 Role 이 USER 인 회원만 접근할 수 있다.
                    // 3-4 : 관리자 페이지(admin)에는 로그인 회원이면서 Role 이 admin 이거나 team1 회원만 접근할 수 있다.
                    http
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/board/write")).authenticated() // 인증(로그인) 된 모든 회원
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/chat")).hasRole("USER") // 로그인된 모든 회원 접속
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/api1")).hasAnyRole("GENERAL") // 일반 회원만 접속
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/api2")).hasAnyRole("OAUTH") // OAUTH 회원만 접속
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/admin")).hasAnyRole("ADMIN", "TEAM1") // ADMIN 또는 TEAM1 회원만 접속
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll(); // 그 외 모든 접속자 허용
        });

            // [3**] 403 에러 메시지는 권한이 없을 때 페이지 접근 차단.
            // 403 에러 페이지를 핸들러(매핑) 하기.
        httpSecurity.exceptionHandling((e) ->
            e.accessDeniedHandler((request, response, accessDeniedException) -> {response.sendRedirect("/error403");  }) // URL 반환 // 403 에러 핸들러
        );


        // [4] CSRF : post/put (Body) 요청을 금지, 특정한 URL 만 post/put 가능하도록 수동 허용
        // 개발 : CSRF 사용 안 함, 개발 환경에서는 끄고 사용하는 경우가 많다.
        httpSecurity.csrf(AbstractHttpConfigurer :: disable); //csrf 끄기. --> post/put 사용할 수 있다.
        // 배포/운영 : 특정한 URL 수동 허용, 운영 환경에서는 안전하게 몇몇개의 REST 만 허용한다.
        // httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("csrf 예외할 URL"));
        // httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/member/signup.do")); // 회원가입 POST 예외

        // [5] 로그인, 시큐리티에서 로그인 기능 [커스텀] 제공. JSON 형식이 아닌 form 형식으로 지원
        httpSecurity.formLogin(loginForm ->
                    loginForm
                            .loginPage("/member/login")             // 로그인 할 view page url 정의
                            .loginProcessingUrl("/member/login.do") // 로그인을 처리 요청 url 정의 // POST 방식 지원
                            .usernameParameter("mid")               // 로그인에 사용한 id 변수명
                            .passwordParameter("mpwd")              // 로그인에 사용한 password 변수명
                            //.defaultSuccessUrl("/")                 // 만약에 로그인 성공시 이동할 page url 정의
                            //.failureUrl("/member/login")            // 만약에 로그인 실패시 이동할 page url 정의

                            // ---> Fetch/Axios(json 형식)에서는 아래와 같은 방법으로 사용한다.
                            .successHandler((request, response, exception) ->{
                                System.out.println("로그인 성공");
                                response.setContentType("application/json"); // 응답 방식을 JSON 변경
                                response.getWriter().println("true");        // JSON 형식의 true 응답하기
                            })
                            .failureHandler((request, response, exception) -> {
                                System.out.println("로그인 실패");
                                response.setContentType("application/json"); // 응답 방식을 JSON 변경
                                response.getWriter().println("false");       // JSON 형식의 false 응답하기.
                            })
                );

        // [6] 로그아웃, 시큐리티에서 로그아웃 기능 [커스텀] 제공
        httpSecurity.logout(logout ->
                logout
                        .logoutUrl("/member/logout.do")  // 로그아웃 처리할 요청 url 정의 // GET 방식 지원
                        .logoutSuccessUrl("/")          // 만약에 로그아웃 성공시 이동할 url 정의
                        .invalidateHttpSession(true)    // 만약에 로그아웃 성공시 (로그인)세션 초기화
                );

        // [7] 로그인을 처리할 서비스 객체 정의
        httpSecurity.userDetailsService(memberService);

        // [8] 시큐리티에서 Oauth2 로그인 페이지와 (커스텀/오버라이딩/재정의)서비스 정의
        httpSecurity.oauth2Login(oauth2Login -> {
            oauth2Login
                    .loginPage("/member/login") // Oauth2 실행 할 URL 페이지 정의
                    // Oauth2 에서 로그인 성공시 유저 정보를 받을 객체 정의
                    .userInfoEndpoint(userinfo -> {userinfo.userService(memberService); });
        });


        // [2] http 객체를 빌드/실행하여 보안 필터 체인 생성
        return httpSecurity.build();
    }

    @Autowired private MemberService memberService;

    // [2] 암호화 : 시큐리티가 회원의 패스워드 검증[로그인]할 때 사용할 암호화 객체, Bcrypt
        // -> 개발자가 직업 암호화를 비교하지 않고, 시큐리티가 자동으로 암호화를 비교한다. (로그인 처리 자동)
    @Bean // 스프링 컨테이너에 메소드 등록 // PasswordEncoder : 시큐리티가 로그인 시 사용할 암호화 인코딩 객체
    public PasswordEncoder encoder(){return new BCryptPasswordEncoder();}

}
