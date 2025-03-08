package korweb.model.dto;

import korweb.model.entity.MemberEntity;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Getter@Setter@ToString@Builder
@NoArgsConstructor@AllArgsConstructor
public class MemberDto {
    private int mno;
    private String mid;
    private String mpwd;
    private String mname;
    private String memail;
    private int mpoint;
    private String mimg;
    private MultipartFile uploadfile; // 업로드 파일 객체

    // Dto -> Entity
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .mid(this.mid)
                //.mpwd(this.mpwd) // 시큐리티 암호화 하기 전

                // + 시큐리티 암호화 한 후, BCrypt 객체를 이용한 암호화 하기
                // 회원가입 할 때 현재 builder 사용하므로 암호화가 적용된다.
                .mpwd(new BCryptPasswordEncoder().encode(this.mpwd))

                .mname(this.mname)
                .memail(this.memail)
                .mimg(this.mimg)
                .build();
    }
}
