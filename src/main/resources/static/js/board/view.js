console.log('view.js opened')

// [1] 개별 게시물 조회하기
cons onFind = () => {

    // 1. 현재 보고자하는 게시물의 번호를 찾기 / 사용자가 클릭한 게시물 번호 찾기
    // board/view?bno = 3, 즉] url 경로상의 queryString bno 존재한다.
    console.log(new URL()) // new URL() : url 정보를 담는 객체 생성
    console.log(new URL(location.href)) // 현재 페이지의 url 정보를 담은 객체 생성
    console.log(new URL(location.href).searchParams) // 현재 페이지의 url 정보 중 queryString 매개변수 반환 속성


}
onFind(); // 페이지가 열릴 때 개별 게시물 조회 함수 실행