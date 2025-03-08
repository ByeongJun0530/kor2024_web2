package korweb.model.dto;

import lombok.*;

@Getter @Setter @ToString
@Builder @AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    private int id; // 제품식별번호
    private String name; // 제품명
    private int price; // 제품 가격
}
