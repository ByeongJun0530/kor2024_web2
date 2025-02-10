package korweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    // * 서버(톰캣/build) 경로 내 이미지 폴더
    // 1. 프로젝트 폴더 명 -> build -> resources -> main -> img 폴더 우클릭 후 'copy path' --> 'absolute path'
    //          - pc 마다 경로가 상이할 수 있으므로 직접 경로 확인
    // 2. 마지막 경로 뒤에 '\\' 넣어준다.
    String uploadPath = "D:\\오병준\\kor2024_web2\\build\\resources\\main\\static\\img\\";

    // 1. 업로드 함수/메소드
    public String fileUpload(MultipartFile multipartFile){
        // (1) 매개변수로 MultipartFile 타입 객체를 받는다. 클라이언트가 보낸 첨부파일이 들어있는 객체
        System.out.println(multipartFile.getOriginalFilename()); // 첨부파일의 파일명을 반환하는 함수
        System.out.println(multipartFile.getName()); // 첨부파일이 들어있는 속성명을 반환하는 함수
        System.out.println(multipartFile.getSize()); // 첨부파일의 용량 반환하는 함수/바이트 단위
        System.out.println(multipartFile.isEmpty()); // 첨부파일이 존재하는지 여부를 반환하는 함수

        // (*) 만약에 서로 다른 파일을 동일한 이름으로 업로드 했을 때 파일명 식별 불가능.
        // 방안 : 파일명 앞에 UUID 난수 텍스트 조합,
            //1. UUID 생성
        String uuid = UUID.randomUUID().toString();
            System.out.println(uuid); // fcc1b41f-9128-4f39-b4dd-0e8c771514bf 텍스트 실행 할때마다 다르게 생성된다. 중복 희박
        // (2) 업로드 경로 와 파일명 조합하기, 업로드 경로 + UUID + 파일명
            // 2. UUID 의 구분자는 '-' 을 사용하므로 파일명에 하이픈이 존재하면 안된다.
            // -> 파일명에 '-' 하이픈 모두 '_' 언더바로 변경 : multipartFile.getOriginalFilename().replaceAll("-","_");
            // * 문자열.replaceAll("수정할 문자", "변경할 문자") : 만약에 문자열 내 수정할 문자가 존재하면 변경할 문자로 치환/변경
            // 예] fcc1b41f-9128-4f39-b4dd-0e8c771514bf-파일명
        String fileName = uuid + "-" + multipartFile.getOriginalFilename().replaceAll("-","_");
        String uploadFile = uploadPath + fileName;
        // (3) 조합된 경로로 file 클래스 객체 만들기
        File file = new File(uploadFile);
        // (4) 업로드 하기, .transferTo( 지정된 경로 ), 예외 발생
        try {multipartFile.transferTo(file);
        } catch (IOException e){System.out.println("파일 업로드 실패 : " + e); return null;} // 만약에 업로드 실패시 null
        return fileName; // 만약에 업로드 성공하면 성공한 파일명 반환
    }

    // 2. 다운로드 함수/메소드

}// cls end
