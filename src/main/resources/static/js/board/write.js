// * 썸머노트 실행
$(document).ready(function() {
$('#summernote').summernote({
    height : 500,
    lang: 'ko-KR',// default: 'en-US'
    placeholder : '게시물 내용을 입력해주세요.'
});
});

// [1] 게시물 등록 요청 함수
const onWrite = () => {
    // [1] 현재 html의 dom 객체 반환
    const cno = document.querySelector('.cno').value;
    const btitle = document.querySelector('.btitle').value;
    const bcontent = document.querySelector('.bcontent').value;
    // [2] 입력받은 값들을 json 보내기 위해 입력받은 값으로 객체 만들기
    const obj = {cno : cno, btitle : btitle, bcontent : bcontent}
    // [3] fetch
    const option = {
                    method : "POST",
                    headers : {'Content-Type':'application/json'},
                    body : JSON.stringify(obj)
                }
    fetch("/board/write.do", option)
        .then(r => r.json())
        .then(data => {
            console.log(data);
            if(data == true){
                alert('글쓰기 성공');
                location.href = `/board?cno=${cno}`
            }else{
                alert('글쓰기 실패 : 로그인 후에 가능합니다.')
            }
        })
        .catch(e => {console.log(e);})
}