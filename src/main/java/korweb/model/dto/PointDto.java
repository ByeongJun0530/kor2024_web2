package korweb.model.dto;


import korweb.model.entity.PointEntity;
import lombok.*;

@Getter @Setter @ToString @Builder // 룸복
@AllArgsConstructor @NoArgsConstructor // 룸복
public class PointDto {
    private int pno; // 포인트 식별자
    private String pcontent; // 포인트 지급내용 : 회원가입 , 로그인 , 글쓰기
    private int pcount; // 포인트 지급수량 : 100 , 1 , -10
    private String cdate; // 지급일

    // dto --> entity 변한 함수
    public PointEntity toEntity(){
        return PointEntity.builder()
                .pno( this.pno )
                .pcontent( this.pcontent )
                .pcount( this.pcount )
                .build();
    }
}
