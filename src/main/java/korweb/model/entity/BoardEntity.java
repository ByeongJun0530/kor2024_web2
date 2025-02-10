package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.BoardDto;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // 엔티티
@Table(name = "board") // 테이블명
public class BoardEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno; // 1. 게시물 번호

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String btitle;// 2. 게시물 제목

    @Column(columnDefinition = "longtext")
    private String bcontent;// 3. 게시물 내용

    @Column
    private int bview;// 4. 게시물 조회수

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity; // 5. 작성자(fK)

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cno")
    private CategoryEntity categoryEntity; // 6. 카테고리 번호(fk)

    // Entity -> toDto 변환 메소드
    // 데이터베이스에 저장 된 엔티티를 조회한 후 dto로 변환해야 하므로 필요하다.
    public BoardDto toDto(){
        return BoardDto.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bview(this.bview)
                .mno(this.memberEntity.getMno())
                .cno(this.categoryEntity.getCno())
                .mid(this.memberEntity.getMid())
                .cname(this.categoryEntity.getCname())
                .cdate(this.getCdate().toString())
                .build();
    }

}
