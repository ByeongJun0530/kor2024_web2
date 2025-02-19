console.log('view.js opened')

// [1] 개별 게시물 조회하기
const onFind = () =>{
    // 1. 현재 보고자하는 게시물의 번호를 찾기 / 사용자가 클릭한 게시물 번호 찾기
    const bno = new URL( location.href ).searchParams.get('bno')
    // 2. fetch
    fetch( `/board/find.do?bno=${ bno }` )
        .then( r => r.json() )
        .then( data => {
            console.log( data );
            document.querySelector('.midbox').innerHTML = data.mid
            document.querySelector('.bviewbox').innerHTML = data.bview
            document.querySelector('.cdatebox').innerHTML = data.cdate

            document.querySelector('.btitle').innerHTML = data.btitle
            document.querySelector('.bcontent').innerHTML = data.bcontent
        })
        .catch( e =>{ console.log(e); })
}
onFind(); // 페이지가 열릴때 개별 게시물 조회 함수 실행

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
/*const onReplyFindAll = () => {
    const bno = new URL(location.href).searchParams.get('bno')
    // fetch
    fetch(`/reply/findall.do?bno=${bno}`)
        .then(r => r.json)
        .then(data => {
            console.log(data);
            const replybox = document.querySelector('.replybox');
            let html = ``;
            // 반복문으로 html 요소 변환
            data.forEach(reply => {
                html += `
                        <div class="reply" data-rno="${reply.rno}" style="display: flex; align-items: center; gap: 10px; margin-bottom: 10px;">
                            <!-- 프로필 이미지 -->
                            <img src="/images/${reply.mimg || 'default.jpg'}" alt="프로필 이미지" style="width: 40px; height: 40px; border-radius: 50%;">

                            <!-- 댓글 내용 -->
                            <div>
                            <p><strong>${reply.mid}</strong> <span style="color: gray; font-size: 12px;">(${reply.cdate})</span></p>
                            <p>${reply.rcontent}</p>
                            </div>
                        </div>
                        `;
            })
            replybox.innerHTML = html;
        })
        .catch(e => console.log(e));
}
*/
// [3] 개별 게시물의 존재하는 댓글 조회 요청 함수
const onReplyFindAll = ( ) => {
	// [준비물] bno
	const bno = new URL( location.href ).searchParams.get("bno");
	// fetch queryString
	fetch( `/reply/findall.do?bno=${bno}` )
		.then( r => r.json() )
		.then( data => {
			console.log( data );
			const replybox = document.querySelector('.replybox')
			let html = ``;
			data.forEach( reply =>{
				html +=`<div class="card mt-3">
						  <div class="card-header">
						  	<img src="/img/${ reply.mimg}" style="width:30px;" />
						    ${ reply.mid }
						  </div>
						  <div class="card-body">
						     ${ reply.rcontent }
						  </div>
						</div>`
			}); // for end
			replybox.innerHTML = html;
		})
		.catch( e => { console.log(e); })
} // class end
onReplyFindAll();















