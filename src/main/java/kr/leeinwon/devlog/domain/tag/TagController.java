package kr.leeinwon.devlog.domain.tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody @Valid TagRequest tagRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(tagRequest));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags(){
        return ResponseEntity.ok(tagService.getTags());
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<TagResponse> deleteTag(@PathVariable Long tagId){
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }


}
