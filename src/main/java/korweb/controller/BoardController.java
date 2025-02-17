package korweb.controller;

import korweb.model.dto.BoardDto;
import korweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BoardController {
    @Autowired private BoardService boardService; // - Board 서비스 객체 주입

    // [1] 게시물 쓰기
    @PostMapping("/board/write.do")
    public boolean boardWrite(@RequestBody BoardDto boardDto){
        return boardService.boardWrite(boardDto);
    }
    // [2] 게시물 전체 조회
    //@GetMapping("/board/findall.do")
    //public List<BoardDto> boardFindAll(){
    //return boardService.boardFindAll();
    //}
    // [2] 게시물 전체 조회
    @GetMapping("/board/findall.do")
    public List<BoardDto> boardFindAll(@RequestParam int cno){
        return boardService.boardFindAll(cno);
    }

    // [3] 게시물 개별 조회
    @GetMapping("/board/find.do")
    public BoardDto boardFind(@RequestParam int bno){
        return boardService.boardFind(bno);
    }
    // [4] 게시물 수정
    @PutMapping("/board/update.do")
    public boolean boardUpdate(@RequestBody BoardDto boardDto){
        return boardService.boardUpdate(boardDto);
    }
    // [4] 게시물 삭제
    @DeleteMapping("/board/delete.do")
    public boolean boardDelete(@RequestParam int bno){
        return boardService.boardDelete(bno);
    }

    // ===================== 댓글 ======================
    // [5] 댓글 쓰기
    @PostMapping("/reply/write.do")
    public boolean replyWrite(@RequestBody Map<String, String> replyDto){ // * DTO 클래스 대신에 Map 컬렉션 활용
        return boardService.replyWrite(replyDto);
    }

    // [6] 특정 게시물의 댓글 전체 조회
    @GetMapping("/reply/findall.do")
    public List<Map<String,String>> replyFindAll(@RequestParam int bno){
        return boardService.replyFindAll(bno);
    }
}





















