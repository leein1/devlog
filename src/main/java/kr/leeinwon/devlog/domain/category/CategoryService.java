package kr.leeinwon.devlog.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if(
                categoryRepository.existsByName(categoryRequest.getName())
        ) {
            throw new IllegalArgumentException("'"+categoryRequest.getName()+"' 은(는) 이미 존재하는 카테고리 이름입니다");
        }

        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();

        return new CategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));

        return new CategoryResponse(category);
    }

    public Category getCategoryEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("카테고리가 존재하지 않습니다"));
    }

    @Transactional
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다"));

        categoryRepository.delete(category);
    }


}
