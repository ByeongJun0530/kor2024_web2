package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.MemberDto;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

// 롬복
@Getter@Setter@ToString@Builder
@AllArgsConstructor@NoArgsConstructor
@Entity // 엔티티
@Table(name = "member") // 테이블명
public class MemberEntity extends BaseTime{
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    private int mno; // 회원번호

    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String mid; // 회원아이디

    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String mpwd; // 회원 비밀번호

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String mname; // 회원 이름

    @Column(nullable = false, unique = true, columnDefinition = "varchar(50)")
    private String memail; // 회원 이메일

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String mimg; // 회원 프로필 사진명

    //@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "MemberEntity", orphanRemoval = true)


    // Entity -> dto
    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno)
                .mid(this.mid)
                .mpwd(this.mpwd)
                .mname(this.mname)
                .memail(this.memail)
                .mimg(this.mimg)
                .build();
    }
}
