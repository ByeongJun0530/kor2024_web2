
// JS 자동완성 : 1.VSCODE 2. 플러그인 : tabnine;

// [1] ( 프로필 업로드 전)회원가입 함수
/*
const onSignUp = ( ) => {
    console.log('함수 실행 됨.');
    // (1) Input dom 가져온다.
    let midInput = document.querySelector('.midInput'); console.log(midInput);
    let mpwdInput = document.querySelector('.mpwdInput')
    let mpwdCheckInput = document.querySelector('.mpwdCheckInput')
    let mnameInput = document.querySelector('.mnameInput')
    let memailInput = document.querySelector('.memailInput')
    // (2) Dom의 value(입력받은 값) 반환 받는다. 
    let mid = midInput.value; console.log(mid);
    let mpwd = mpwdInput.value;
    let mpwdCheck = mpwdCheckInput.value;
    let mname = mnameInput.value;
    let memail = memailInput.value;
    // (!!! 생략) 다양한 유효성 검사 코드

    // (3) 입력받은 값들을 객체와 한다. 
    let dataObj = {mid : mid, mpwd : mpwd, mname : mname, memail : memail}
    console.log(dataObj);
    // (4) fetch 옵션
    const option = {
        method : "POST", // - HTTP 통신 요청 보낼 때 사용할 METHOD 선택 
        headers : {'Content-Type' : 'application/json'}, // HTTP 통신 요청 보낼 때 header body(본문) 타입 설정 
        body : JSON.stringify(dataObj) // HTTP 통신 요청 보낼 때 Body(본문) 자료 대입하는데 
        // JSON.stingify(객체) : 객체 타입 --> 문자열 타입 변환, HTTP 는 문자열 타입만 전송이 가능하다. 
    }
    // (5) fetch 통신 
    fetch("/member/signup.do", option) // fetch('통신할 URL', 옵션)
        .then(r => r.json()) // .then() 통신 요청 보내고 응답 객체를 반환받고 .json() 를 이용한 응답 객체를 json타입으로 변환 
        .then(d => {         // .then() json으로 변환된 자료를 실행문 처리 
            // (6) fetch 응답에 따른 화면 구현 
            if(d == true){alert('가입 등록 완료'); location.href="/member/login";} // 만일 응답 자료가 true이면 성공, 로그인 페이지로 이동 
            else{alert('가입실패')} // 만일 응답 자료가 false이면 실패 안내 
        })
        .catch(r => {alert('가입오류 : 관리자에게 문의')}) // 통신 오류가 발생하면 오류 메시지 안내
*/

// [2] (업로드) 회원가입 함수
const onSignUp = () => {
    // - 입력된 값을 하나씩 가져오는 방식이 아닌 form 전체를 한 번에 가져오기
    // [1] 전송할 form dom 객체를 가져온다.
    const signupForm = document.querySelector('#signupForm');
    console.log(signupForm); // 가져온 form 전체를 가져왔는지 확인
    // * 폼 전체를 전송할 때는 controller 와 dto 멤버변수와 form 안에 있는 input 의 name 속성명 동일하게
    // <input name="mid"> <--- 동일 ---> MemberDto(private String mid;)
    // [2] form dom 객체를 바이트로 변환한다. new FormData(form dom 객체) : 지정한 dom 객체를 바이트로 변환
    const signupFormData = new FormData(signupForm);
    console.log(signupFormData); // 'application/json' 형식이 아닌 'multipart-form-data' 형식으로 전송하기 위해서

    // [3] application-json 이 아닌 multipart-form-data 형식의 fetch 설정하는 방법
    const option = {
        method : "POST",
        // content-type 생략하면 자동으로 multipart-form-data 설정된다
        body : signupFormData
        // JSON.stringify() 안하는 이유 : 폼 전송 해야하므로 생략
    }
    // [4] fetch 사용
    fetch('/member/signup.do', option)
         .then(r => r.json()) // .then() 통신 요청 보내고 응답 객체를 반환받고 .json() 를 이용한 응답 객체를 json타입으로 변환
         .then(d => {         // .then() json으로 변환된 자료를 실행문 처리
               // (6) fetch 응답에 따른 화면 구현
                  if(d == true){alert('가입 등록 완료'); location.href="/member/login";} // 만일 응답 자료가 true이면 성공, 로그인 페이지로 이동
                  else{alert('가입실패')} // 만일 응답 자료가 false이면 실패 안내
               })
         .catch(r => {alert('가입오류 : 관리자에게 문의')}) // 통신 오류가 발생하면 오류 메시지 안내

}// f end






















