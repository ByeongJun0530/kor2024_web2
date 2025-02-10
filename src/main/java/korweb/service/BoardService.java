package korweb.service;

import korweb.model.dto.BoardDto;
import korweb.model.dto.MemberDto;
import korweb.model.entity.BoardEntity;
import korweb.model.entity.CategoryEntity;
import korweb.model.entity.MemberEntity;
import korweb.model.entity.ReplyEntity;
import korweb.model.repository.BoardRepository;
import korweb.model.repository.CategoryRepository;
import korweb.model.repository.MemberRepository;
import korweb.model.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class BoardService {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository; // member 엔티티 조작하는 인터페이스
    @Autowired private BoardRepository boardRepository; // board 엔티티 조작하는 인터페이스
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ReplyRepository replyRepository;

    // [1] (회원제) 게시물 쓰기
    public boolean boardWrite(@RequestBody BoardDto boardDto){
        // (1) 사용자로부터 전달받은 boardDto(btitle, bcontent, cno) 를 엔티티로 변환
            // 0. boardDto 를 Entity 로 변환
        BoardEntity boardEntity = boardDto.toEntity();
            // 1. 게시물 작성자는 현재 로그인 된 회원이므로 세션에서 현재 로그인 된 회원번호를 가져온다
            // - 현재 로그인 된 세션 객체 조회
            MemberDto loginDto = memberService.getMyInfo();
            // - 만약에 로그인 된 상태가 아니면 글쓰기 종료
            if (loginDto == null){return false;}
            // - 로그인 된 상태이면 회원번호 조회
            int loginMno = loginDto.getMno();
            // - 로그인 된 회원 엔티티를 게시물 엔티티에 대입한다.
            MemberEntity loginEntity = memberRepository.findById(loginMno).get();
        boardEntity.setMemberEntity(loginEntity);
            // 2. 게시물 카테고리 cno 를 entity 조회해서 게시물 엔티티에 대입한다. .findById(pk번호) 지정한 pk번호 엔티티 조회
            CategoryEntity categoryEntity = categoryRepository.findById(boardDto.getCno()).get();
        boardEntity.setCategoryEntity(categoryEntity);
        // (2) 엔티티 .save(저장할 엔티티)
        BoardEntity saveBoardEntity = boardRepository.save(boardEntity);
        // (3) 만약에 게시물 등록에 성공했다면, 등록 성공이면 true 반환
        if (saveBoardEntity.getBno() > 0){return true;}
        else {return false;} // 게시물 작성 실패
    }
    // [2] 게시물 전체 조회
    public List<BoardDto> boardFindAll(int cno){
        // (1) 모든 게시물의 엔티티를 조회
        List<BoardEntity> boardEntityList = boardRepository.findAll();
            // cno를 이용한 동일한 cno 게시물 정보 찾기
        // (2) 모든 게시물의 엔티티를 DTO로 변환
            // - DTO 를 저장할 리스트 선언
            List<BoardDto> boardDtoList = new ArrayList<>();
            // - 반복문을 이용하여 모든 엔티티를 dto 로 변환하기
                // 만약에 현재 조회중인 게시물의 카테고리가 선택한 카테고리와 같
                // [1] 리스트변수명.foreach( 반복 변수명 -> {실행문;})
                // [1-2] for 문 : for (int index = 0; index <= boardDtoList.size()-1; index++){
        //            boardEntityList.get(index);
        //        }
        boardEntityList.forEach(entity -> {
            // [2] 엔티티 --> dto 변환
            if (entity.getCategoryEntity().getCno() == cno){
                BoardDto boardDto = entity.toDto();
                // [3] 변환된 dto를 dto 리스트에 담는다
                boardDtoList.add(boardDto);
            }
            else { }
        });
        // (3)결과를 리턴한다.
        return boardDtoList;
    }

    // [3] 게시물 개별 조회
    public BoardDto boardFind(@RequestParam int bno){
        // (1) 조회할 특정 게시물의 번호를 매개변수로 받는다
        // (2) 조회할 특정 게시물 번호의 엔티티를 조회한다. .findById() 메소드는 반환타입이 optional 이다. 조회된 엔티티 여부 메소드 제공
        Optional<BoardEntity> optional = boardRepository.findById(bno);
        // (3) 만약에 조회된 엔티티가 존재하면 true/false
        if (optional.isPresent()) {
            // (4) optional 에서 엔티티 꺼내기.get()
            BoardEntity boardEntity = optional.get();
            // (5) 엔티티를 dto로 변환
            BoardDto boardDto = boardEntity.toDto();

                // * 현재 게시물의 댓글 리스트 조회
                // 1. 모든 게시물 댓글 조회
            List<ReplyEntity> replyEntityList = replyRepository.findAll();
                // 2. 모든 댓글 DTO/Map 로 변환한 객체들 저장할 리스트 선언 --> ReplyDto 대신 MAP 컬렉션 이용한 방법
                    // List 컬렉션 : [값1, 값2, 값3] vs Map 컬렉션 : { key : value, key2 : value2, key3 : value3 }
            List<Map<String, String>> replylist = new ArrayList<>();
                // 3. 엔티티를 MAP로 변환
            replyEntityList.forEach((replyEntity) -> {
                // * 만약 현재 조회중인 게시물 번호와 댓글리스트 내 반복중인 댓글의 게시물 번호와 같다면 조회
                if (replyEntity.getBoardEntity().getBno() == bno) {
                    // 4. map 객체 선언
                    Map<String, String> map = new HashMap<>();
                    // 5. 맵 객체에 하나씩 key : value (엔트리) 으로 저장한다.
                    map.put("rno", replyEntity.getRno() + ""); // 숫자타입 + "" => 문자타입
                    map.put("rcontent", replyEntity.getRcontent()); // 숫자타입 + "" => 문자타입
                    map.put("cdate", replyEntity.getCdate().toLocalDate().toString()); // 생성된 날짜와 시간에서 날짜만 추출
                    map.put("mid", replyEntity.getMemberEntity().getMid()); // 댓글 작성자 아이디
                    map.put("mimg", replyEntity.getMemberEntity().getMimg()); // 댓글 작성자 프로필
                    // 6. map 리스트에 담는다.
                    replylist.add(map);
                }
            });
            // 7. 반복문이 종료된 후 boardDto에 댓글리스트 담기
            boardDto.setReplylist(replylist);

            // (6) dto 결과 반환
            return boardDto;
        }
        return null; // 조회 결과 엔티티가 없으면 null 반환
    }
    // [4] 게시물 수정
    public boolean boardUpdate(BoardDto boardDto){
        return false;
    }
    // [5] 게시물 삭제
    public boolean boardDelete(int bno){
        return false;
    }

    // ============================== 댓글 =============================
    // [6] 댓글 쓰기
    public boolean replyWrite(Map<String, String> replyDto){ // * DTO 클래스 대신에 Map 컬렉션 활용
        // 1. 현재 로그인 된 회원번호 조회
        MemberDto memberDto = memberService.getMyInfo();
        // 2. 로그인 된 정보 없으면 함수 종료
        if (memberDto == null)return false;
        // 3. [로그인 중]
        // 3. 회원 엔티티 조회
        MemberEntity memberEntity = memberRepository.findById(memberDto.getMno()).get();
        // 작성할 댓글이 위치한 조회중인 게시물 엔티티 조회
            // Integer.parseInt("문자열") => 정수타입 반환 함수
        int bno = Integer.parseInt(replyDto.get("bno"));
        BoardEntity boardEntity = boardRepository.findById(bno).get();
        // 4. 입력받은 매개변수 map를 entity로 변환
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setRcontent(replyDto.get("rcontent"));
        replyEntity.setMemberEntity(memberEntity); // 작성자 등록
        replyEntity.setBoardEntity(boardEntity); // 댓글이 위치한 게시물

        // 5. 생성한 엔티티 저장
        ReplyEntity saveEntity = replyRepository.save(replyEntity);
        if (saveEntity.getRno() > 0){ // 댓글번호 생성시 등록 성공
            return true;
        }else { // 아니면 등록 실패
            return false;
        }
    }

    // [7] 댓글 전체 조회
    public List<Map<String,String>> replyFindAll(int bno){

        // 1. 모든 댓글 엔티티 조회
        List<ReplyEntity> replyEntityList = replyRepository.findAll();
        // 2. 모든 댓글 map 저장할 리스트 선언
        List<Map<String, String>> replylist = new ArrayList<>();
        // 3. 모든 댓글 엔티티 반복문으로 조회
        replyEntityList.forEach((replyEntity -> {
            // 4.
            Map<String, String> map = new HashMap<>();
            // 5. 맵 객체에 하나씩 key : value (엔트리) 으로 저장한다.
            map.put("rno", replyEntity.getRno() + ""); // 숫자타입 + "" => 문자타입
            map.put("rcontent", replyEntity.getRcontent()); // 숫자타입 + "" => 문자타입
            map.put("cdate", replyEntity.getCdate().toLocalDate().toString()); // 생성된 날짜와 시간에서 날짜만 추출
            map.put("mid", replyEntity.getMemberEntity().getMid()); // 댓글 작성자 아이디
            map.put("mimg", replyEntity.getMemberEntity().getMimg());
            // 6. map 리스트에 담는다
            replylist.add(map);
        }));
        return null;
    }
}





















