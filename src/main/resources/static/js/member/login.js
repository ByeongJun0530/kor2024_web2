// [2] 로그인 함수
const onLogin = () => {
    console.log('함수 실행 됨.')
    // (1) input dom 가져오기 
    let midInput = document.querySelector('.midInput')
    let mpwdInput = document.querySelector('.mpwdInput')
    // (2) 가져온 DOM의 value(입력값) 가져오기
    let mid = midInput.value;
    let mpwd = mpwdInput.value;
    // (!!) 유효성 검사 생략

    // (3) 입력받은 값들을 보낼 객체 만들기
    let dataObj = {mid : mid, mpwd : mpwd}; console.log(dataObj);
    // (4) fetch 함수 옵션 정의
    const option = {method : "POST", headers : {'Content-Type' : 'application/json'},
    body : JSON.stringify(dataObj)
    }
    // (5) fetch 함수 실행 
    fetch('/member/login.do', option)
        .then(r => r.json())
        .then(d => {
            if(d == true){alert('로그인 성공'); location.href = "/"}
            else{alert('회원 정보가 일치하지 않습니다.');}
        })
        .catch(e => {alert('시스템 오류 : 관리자에게 문의하세요.'); console.log(e)})
    // (6) 결과에 따른 화면 제어 
}
