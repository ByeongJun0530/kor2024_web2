// [1] 비동기 함수란?
const test1 = () => {
    console.log("test1");
    const response = axios.get("/students")
    console.log(response.data);
    console.log("test2");
    // 실행순서 : 첫번째 콘솔---> 두 번째 콘솔(undefined) --> 세 번째 콘솔
    // 즉] JS 흐름이 AXIOS 이용하여 요청 보내고 응답을 기다리지 않고 두 번째 콘솔을 출력 헀으므로 undefined
}
test1();

// [2] 동기 함수란? 1.함수 내 매개변수() 앞에 'async' 키워드 삽입 2.axios/fetch 앞에 'await' 키워드 삽입
const test2 = async() => {
    console.log("(2)test1");
    const response = await axios.get("/students")
    console.log(response.data);
    console.log("(2)test2");
    // 실행순서 : 첫번째 콘솔---> 두 번째 콘솔 --> 세 번째 콘솔
    // 즉] JS 흐름이 AXIOS 이용하여 요청 보내고 응답을 올 때까지 기다린다. 응답이 오면 다음 코드를 실행한다.
}
test2();

// [4] 학생 점수 전체 조회 (동기화)
const onFindAll = async () => {
    // fetch 대신에 axios 사용
    // (1) Axios 이용한 Spring controller 매핑
    const response = await axios.get("/students")
    // (2) 응답 본문 꺼내기
    const data = response.data;
    // (3) 반복문을 이용하여 html 에 출력한다.
        // 어디에
        const tbody = document.querySelector("tbody")
        // 무엇을
        let html = '';
        data.forEach((student) => {
            html += `<tr>
                        <td>${student.sno}</td>
                        <td>${student.name}</td>
                        <td>${student.kor}</td>
                        <td>${student.math}</td>
                     </tr>`
        })
        // 출력
        tbody.innerHTML = html;
}
onFindAll();

// [3] 학생 점수 등록 버튼 클릭했을 때.
const onSave = async () => {
    // 1. 입력받은 값 가져오기
    const name = document.querySelector('.name').value;
    const kor = document.querySelector('.kor').value;
    const math = document.querySelector('.math').value;
    // 2. 객체화
    // const obj = {name : name,kor : kor,math : math};
    // JS 에서는 객체 속성명과 대입 변수명이 같다면 속성명 생략 가능.
    const obj = {name ,kor, math}

    // [선택2] axios + 동기 : 'application/json' 언급하지 않아도 'application/json' 자동 사용
    const response = await axios.post("/students", obj) // then 함수 대신에 변수에 리턴 받는 형식은 무조건 동기화
    console.log(response.data); // response 응답(정보)객체, response.data 응답 본문 내용
    if(response.data == 1){ // 글쓰기 성공
        alert('글쓰기 성공'); onFindAll();
    }else{
        alert('글쓰기 실패');
    }
    // [선택1] fetch
        /*
        const option = {
            method : "POST",
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(obj)
        }
        fetch("/students", option)
            .then(response => response.json())
            .then(data => {console.log(data)})
            .catch(error => console.error(error))
        */
}