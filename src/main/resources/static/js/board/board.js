
// *js 열렸는지 확인
console.log('board.js open')

// *현재 URL 쿼리스트링 매개변수 가져오기
    // 현재 페이지의 URL 정보가 담긴 객체 생성 
console.log(new URL(location.href)); 
    // 현재 페이지의 URL 쿼리스트링 정보 속성 반환 
console.log(new URL(location.href).searchParams) 
    // 현재 페이지의 URL 쿼리스트링 속성 중 'cno' 반환 
console.log(new URL(location.href).searchParams.get('cno')) 

// [1] 게시물 전체 조회 요청 함수 
const findAll = () => {
    // 1. 현재 페이지의 URL 쿼리스트링 속성 중 'cno' 반환 
    const cno = new URL(location.href).searchParams.get('cno')
    // 2. fetch 
    const option = {method : "GET"};
    // 3. fetch
    fetch(`/board/findall.do?cno=${cno}`, option)
        .then(r => r.json())
        .then(data => {
            // 4. 요청결과 응답 자료 확인 
            console.log(data);
            // 5. html 출력할 구역 dom 가져오기 
            const tbody = document.querySelector('tbody');
            // 6. 출력할 html 저장하는 변수 선언
            let html = ``;
            // 7. 응답 자료를 반복문 이용하여 하나씩 순회해서 html 누적으로 더해주기 
            data.forEach(board => {
                html += `<tr>
                            <td>${board.bno}</td>
                            <td><a href = "/board/view?bno=${board.bno}">${board.btitle}</a></td>
                            <td>${board.mid}</td>
                            <td>${board.bview}</td>
                            <td>${board.cdate}</td>
                        </tr>`
            });
            // 8. 반복문 종료 후 html 변수에 누적으로 
            tbody.innerHTML = html;
        })
        .catch(e => console.log(e))
}
findAll();