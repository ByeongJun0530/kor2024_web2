package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.PointDto;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.format.DateTimeFormatter;

@Getter@Setter@ToString@Builder // 룸복
@AllArgsConstructor@NoArgsConstructor // 룸복
@Entity // 엔티티
@Table( name = "point") // 테이블명
public class PointEntity extends BaseTime {

    @Id // pk
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int pno; // 포인트 식별자

    @Column( nullable = false  , columnDefinition = "varchar(30)")
    private String pcontent; // 포인트 지급내용 : 회원가입 , 로그인 , 글쓰기

    @Column( nullable = false , columnDefinition = "int" )
    private int pcount; // 포인트 지급수량 : 100 , 1 , -10

    @ManyToOne  // FK
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    // entity --> dto 변환함수
    public PointDto toDto(){
        return PointDto.builder()
                .pno( this.pno )
                .pcontent( this.pcontent )
                .pcount( this.pcount )
                .cdate( this.getCdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) )
                .build();
    }
}

