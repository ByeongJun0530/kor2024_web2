package korweb.service;

import korweb.model.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service // 스프링 컨테이너에 빈(인스턴스) 등록, springMVC 에서 비지니스 로직 담당
// 비지니스 로직 : 어떠한 기능의 핵심이 되는 코드, 예] 회원가입에서 '저장'로직
public class StudentService {

    @Autowired private StudentMapper studentMapper;
    // [1] 학생 점수 등록
    public int save(Map<String, Object> map){
        System.out.println("StudentService.save");
        // (1) insert 하기 전 map 매개변수
        System.out.println("map = " + map);  // sno 미포함
        // (*) insert 실행
        int result = studentMapper.save(map);
        // (2) insert 실행 후 매개변수
        System.out.println("map = " + map);
        // sno 포함, save 처리 이후 자동으로 생성된 pk 번호 대입되었다.
        return result;
    }
    // [2] 학생 전체 조회
    public List<Map<String, Object>> findAll(){
        System.out.println("StudentService.findAll");
        //return null;
        return studentMapper.findAll();
    }

    // [3]
    public List<Map<String,Object>> findStudentScores(int minKor, int minMath){
        return studentMapper.findStudentScore(minKor, minMath);
    }

    // [4]
    public boolean saveAll(List<Map<String, Object>> list){
        return studentMapper.saveAll(list);
    }
}
