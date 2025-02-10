console.log('view.js opened')

// [1] 개별 게시물 조회하기
const onFind = () => {

    // 1. 현재 보고자하는 게시물의 번호를 찾기 / 사용자가 클릭한 게시물 번호 찾기
    // board/view?bno = 3, 즉] url 경로상의 queryString bno 존재한다.
    // console.log(new URL()) // new URL() : url 정보를 담는 객체 생성
    // console.log(new URL(location.href)) // 현재 페이지의 url 정보를 담은 객체 생성
    // console.log(new URL(location.href).searchParams) // 현재 페이지의 url 정보 중 queryString 매개변수 반환 속성

    const bno = new URL(location.href).searchParams.get('bno');

    // 2. fetch
    fetch(`/board/find.do?bno=${bno}`)
        .then(r => r.json())
        .then(d => {
            console.log(d);
            document.querySelector('.midbox').innerHTML = d.mid
            document.querySelector('.bviewbox').innerHTML = d.bview
            document.querySelector('.cdatebox').innerHTML = d.cdate

            document.querySelector('.btitle').innerHTML = d.btitle
            document.querySelector('.bcontent').innerHTML = d.bcontent
        })
        .catch(e => console.log(e))
}
onFind(); // 페이지가 열릴 때 개별 게시물 조회 함수 실행

// [2] 댓글 쓰기 요청 함수, 실행조건 : 댓글 작성 버튼 클릭시
const onReplyWrite = () => {
    // 1. 입력받은 값 가져오기
    const rcontentInput = document.querySelector('.rcontentInput')
    const rcontent = rcontentInput.value;
    // 2. 현재 게시물 번호, url 쿼리스트링
    const bno = new URL(location.href).searchParams.get('bno')
    // 3. 객체화
    const obj = {rcontent : rcontent, bno : bno}
    // 4. fetch
    const option = {
        method : "POST",
        headers : {'Content-Type' : 'application/json'},
        body : JSON.stringify(obj)
    }
    fetch('/reply/write.do', option)
        .then(r => r.json)
        .then(d => {
            if(d == true){
                alert('댓글 등록 성공')
                onReplyFindAll(); // 성공시 새로고침
            }else{
                alert('댓글 등록 실패')
            }
        })
        .catch(e => console.log(e))
}

// [3] 개별 게시물의 댓글 조회 요청 함수
const onReplyFindAll = () => {

}