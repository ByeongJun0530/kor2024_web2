// [1] 로그인 정보 요청 
const getLoginMid = () => {
    //  fetch 함수 활용하여 현재 로그인 상태 체크 
    // 1. fetch 옵션
    const option = {method : "GET"}
    // 1. 출력할 위치 DOM 가져오기
    let memberBox = document.querySelector('.memberBox')
    let html = '';
    fetch('/member/myinfo.do', option)
        // SyntaxError: Unexpected token 'a', "asd" is not valid JSON
        .then(r => r.json()) // String controller 에서 String 타입으로 반환하는 경우에는 text() // day 70 : dto 반환하므로 json() 변경
        .then(d => {
            // 로그인 상태에 따라 버튼 활성화 여부 다르게 표현
            console.log('로그인 상태')
            // 로그아웃 버튼, 마이페이지, 로그인된 아이디 활성화
            html += `
                 <li class="nav-item">
                    <a class="nav-link" href="#">
                        <img
                            src="${d.mimg.includes('http') ? d.mimg : '/img/'+d.mimg}"
                            style="width:50px; height:50px; border-radius:30px; border : 1px"/>
                        ${d.mid}님
                    <span class = "pointBox"></span> </a>
                 </li>
                 <li class="nav-item"><a class="nav-link" href="#" onclick = "logOut()">로그아웃</a></li>
                 <li class="nav-item"><a class="nav-link" href="/member/info">마이페이지</a></li>`
            // 4. 출력하기
            memberBox.innerHTML = html;
            // 5. 포인트 지급 불러오기
            myPointInfo();
        }) 
        .catch(e => {
            console.log(e);
            console.log('비 로그인 상태');
            // 3. 회원가입 버튼, 로그인 버튼 활성화
            html += `<li class="nav-item"><a class="nav-link" href="/member/signup">회원가입</a></li>
            <li class="nav-item"><a class="nav-link" href="/member/login">로그인</a></li>`
            // 출력하기
            memberBox.innerHTML = html;
        })
} // f end
getLoginMid(); // JS가 실행될 때. 로그인 정보 요청 함수 호출 

// [2] 로그아웃 함수 
const logOut = () => {
    // 시큐리티 이후에는 fetch로 로그아웃이 아닌 get 방식으로 시큐리티 로그아웃을 요청한다.
    location.href="/member/logout.do";
    // 1. fetch option
    //const option = {method : "GET"}
    // 2. fetch
    //fetch('/member/logout.do', option)
        //.then(r => r.json())
        //.then(d => {
            //if(d == true){alert("로그아웃 성공"); location.href = "/member/login";}
        //})
        //.catch(e => console.log(e))
} // f end

// [3] 내 포인트 함수
const myPointInfo = () => {
    // 1. fetch 
    fetch("/member/point/info.do", {method : "GET"})
        .then(r => r.json())
        .then(d => { console.log(d);
            let pointBox = document.querySelector('.pointBox')
            let html = `(${d}Point)`
            pointBox.innerHTML = html;
        })
        .catch(e => console.log(e))
}

/*
// 클라이언트 웹 소켓 연결
const loginSocket = new WebSocket('ws://localhost:8080/socket/login')
// 로그인 알림 받기
loginSocket.onopen = (event) => {
    console.log('서버 소켓 연동 성공')
}
// 로그인 알림받기
loginSocket.onmessage = (event) => {
    console.log(event.date);
    
}
// 로그인 성공시 서버에게 알림 전송

// 토스트 js
const toastTrigger = document.getElementById('liveToastBtn')
const toastLiveExample = document.getElementById('liveToast')

if (toastTrigger) {
  const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
  toastTrigger.addEventListener('click', () => {
    toastBootstrap.show()
  })
}
*/















