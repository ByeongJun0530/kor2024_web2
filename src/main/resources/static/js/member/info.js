// [1] 마이페이지에서 (로그인 된) 내 정보 불러오기 
const getMyInfo = () => {
    // 1. fetch 이용한 내 정보 요청 응답 받기 
    fetch('/member/myinfo.do', {method : 'GET'})
    .then(r => r.json())
    .then(d => { console.log(d);
        if(d != ''){ // 응답 결과가 존재하면
            document.querySelector('.midInput').value = d.mid;
            document.querySelector('.mnameInput').value = d.mname;
            document.querySelector('.memailInput').value = d.memail
        };
    })
    .catch(e => console.log(e))
} // f end 
getMyInfo(); // info.html이 열릴 때 내정보 불러오기 함수 실행

// [2] 마이페이지 에서 (로그인 된) 회원 탈퇴 요청
const onDelete = () => {
    // * 예 아니오 형식으로 탈퇴 여부 묻고 아니요 이면 탈퇴 중지 
    let result = confirm('정말 탈퇴 하시겠습니까?');
    if(result == false){return;}
    // 1. fetch 이용한 회원탈퇴 요청과 응답 받기 
    fetch('/member/delete.do', {method : "DELETE"})
        .then(r => r.json())
        .then(d => {
            if(d == true){alert('회원 탈퇴 성공'); location.href = '/';}
            else{alert('회원 탈퇴 실패')}
        })
        .catch(e => console.log(e))
}

// [3] 마이페이지에서 로그인된 내 포인트 지급 전체 내역 조회
const getPointInfo = () => {
    // 1. fetch 이용한 내 정보 요청 및 응답
    fetch("/member/point/list.do", {method : "GET"})
        .then(r => r.json())
        .then(d => { console.log(d);
            if(d != ''){
                let pointTable = document.querySelector('.pointTable')
                let html = ``;
                d.forEach((point, index) => {
                    html += `<tr> <th> ${ index+1 } </th> <td> ${ point.cdate }</td> <td> ${ point.pcontent} </td> <td> ${ point.pcount} </td> </tr>`
                })
                pointTable.innerHTML = html;
            }
        })
        .catch(e => console.log(e))
}
getPointInfo();












