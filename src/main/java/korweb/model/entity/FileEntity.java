package korweb.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // 엔티티
@Table(name = "file") // 테이블명
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno; // 첨부파일 번호(pk)

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String fname; // 첨부파일명

    // 3. 첨부파일이 위치한 게시물 번호
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;
}
