package korweb.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 자체 매핑이 아닌 상속 매핑
@EntityListeners(AuditingEntityListener.class) // 엔티티에 변화를 감지
public class BaseTime {
    @CreatedDate // 엔티티 생성 날짜/시간 자동 주입
    private LocalDateTime cdate;

    @LastModifiedDate // 엔티티 수정 날짜/시간 자동 주입
    private LocalDateTime udate;
}
