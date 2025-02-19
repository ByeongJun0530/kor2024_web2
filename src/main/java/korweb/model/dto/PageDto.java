package korweb.model.dto;

import lombok.*;

@Getter
@Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class PageDto {

    private long totalcount;
    private int page;
    private int totalpage;
    private int startbtn;
    private int endbtn;
    // + Object 는 자바의 최상위 클래스이므로 모든 타입의 자료들을 저장할 수 있다.
    // 즉 data 에서 List<boardDto> 혹은 List<ReplyDto>
    private Object data;

}
