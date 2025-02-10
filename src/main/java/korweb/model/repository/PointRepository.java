package korweb.model.repository;

import korweb.model.entity.MemberEntity;
import korweb.model.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Integer> {
    // 지정한 회원의 포인트 목록 내역 조회
    List<PointEntity> findByMemberEntity(MemberEntity memberEntity);
}
