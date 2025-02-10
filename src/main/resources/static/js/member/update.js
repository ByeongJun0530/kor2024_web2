// [1] 수정페이지 들어왔을 때 수정 하기 전 기존 데이터 표시
const getMyInfo = () => {
    fetch('/member/myinfo.do', {method : "GET"})
        .then(r => r.json())
        .then(d => {
            if(d != ''){
                document.querySelector('.midInput').value = d.mid;
                document.querySelector('.mnameInput').value = d.mname;
                document.querySelector('.memailInput').value = d.memail;
            }
        }).catch(e => console.log(e))
};
getMyInfo(); // update.html이 열릴 때 내정보 조회 함수 

// [2] 수정 버튼 클릭했을 때. 수정처리 
const onUpdate = () => {
    
    // 1. 입력받은 input value 값 가져오기 
    let mname = document.querySelector('.mnameInput').value;
    let memail = document.querySelector('.memailInput').value;
    // 2. 객체화 
    let dataObj = {mname : mname, memail : memail}
    // 3. fetch'
    const option = {
        method : "PUT",
        headers : {'Content-Type' : 'application/json'},
        body : JSON.stringify(dataObj)
    };
    fetch('/member/update.do', option)
        .then(r => r.json())
        .then(d => {
            if(d == true){alert('수정 성공'); location.href = "/member/info";}
            else{alert('수정 실패');}
        }).catch(e => console.log(e))
}