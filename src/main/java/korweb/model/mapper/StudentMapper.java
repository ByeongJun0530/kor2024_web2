package korweb.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper // 스프링 컨테이너에 빈 등록, mybatis 와 연동되는 인터페이스 임 명시
public interface StudentMapper {

    // [1] 학생 점수 등록(추상)
    @Insert("insert into student (name, kor, math) values (#{name}, #{kor}, #{math})")
    @Options(useGeneratedKeys = true, keyProperty = "sno")
    // useGeneratedKeys : auto_increment 로 생성된 pk 번호 반환을 뜻한다.
    // keyProperty : pk 번호를 가지는 pk 필드(속성) 명 뜻한다.
    // --> insert 성공한 자동으로 생성된 pk 번호를 매개변수에 저장한다 .
    int save(Map<String, Object> map);

    // [2] 학생 전체 조회(추상)
    // @Select("select* from student") // 정적 쿼리
    @Select(("<script> select* from student </script>")) // 동적 쿼리
    List<Map<String, Object>> findAll();

    // [3] 특정한 점수 이상의 학생 조회 ( 동적 쿼리 표현 )
    // @Select("동적쿼리")
    // (1) JAVA15 이상부터 (강의는 17) """문자열""" 템플릿 지원 : 문자열 입력할 때 다음줄로 이어지는 방법
    /*
        [ JAVA15미만 ] String text = "안녕하세요"
                                        + "유재석입니다.";  +더하기 연산자 이용한 문자열 연결
        [ JAVA15이상 ] String text = """ 안녕하세요
                                           유재석입니다."""; 템플릿 이용한 문자열 연결, JS : `백틱 연산자
    */
    // (2) 1 = 1 : SQL 에서 강제로 true 가 필요할 때 사용하는 방법, 주로 다음 조건을 동적으로 처리할 때 사용된다.
    // 즉] 다음 조건이 있을 수도 있고, 없을 수도 있을 때
    @Select("""
            <script>
            select* from student where 1 = 1
                <if test = "minKor != null">
                    and kor >= #{minKor}
                </if>
                <if test = "minMath != null">
                    and math >= #{minMath}
                </if>
            </script>
            """)
    List<Map<String, Object>> findStudentScore(int minKor, int minMath);
    // minKor 가 80이고, minMath 가 null 일 때 : select* from student where 1 = 1 and kor >= 80
    // minKor 가 없고, minMath 없을 때 : select* from student where 1 = 1
    // minKor 가 90이고, minMath 가 70일 때 : select* from student where 1 = 1 and kor >= 90 and math >= 70

    // [4] 여러명의 학생 등록하기 (배치)
    @Insert("""
            <script>
                insert into student (name, kor, math) values
                <foreach collection="list" item="student" separator=",">
                    (#{student.name}, #{student.kor}, #{student.math})
                </foreach>
            </script>
            """)
    boolean saveAll(List<Map<String,Object>> list);
    /*
        * SQL 에서 레코드 삽입 방법
            1. 레코드 1개를 삽입 : insert into 테이블명 (필드명, 필드명, 필드명) values (값1, 값2, 값3)
            2. 레코드 여러개 삽입 : insert into 테이블명(필드명, 필드명) values (값1, 값2), (값3, 값4), (값5, 값6)
     */

}














