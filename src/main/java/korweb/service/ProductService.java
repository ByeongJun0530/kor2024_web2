package korweb.service;

import korweb.model.dto.ProductDto;
import korweb.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {
    @Autowired private ProductMapper productMapper;

    // [1] 제품 등록 서비스
    public int save(ProductDto productDto){
        return productMapper.save(productDto);
    }

    // [2] 제품 전체 조회 서비스
    public List<ProductDto> findAll(){
        return productMapper.findAll();
    }

    // [3] 제품 개별 조회 서비스
    public ProductDto find(int id){
        return productMapper.find(id);
    }

    // [4] 제품 개별 수정
    public boolean update(ProductDto productDto){
        return productMapper.update(productDto);
    }

    // [5] 제품 개별 삭제
    public boolean delete(int id){
        return productMapper.delete(id);
    }
}
