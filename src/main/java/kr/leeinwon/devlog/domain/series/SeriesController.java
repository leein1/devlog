package kr.leeinwon.devlog.domain.series;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;

    @PostMapping
    public ResponseEntity<SeriesResponse> createSeries(@RequestBody @Valid  SeriesRequest seriesRequest) {
        SeriesResponse seriesResponse = seriesService.createSeries(seriesRequest );
        return ResponseEntity.status(HttpStatus.CREATED).body(seriesResponse);

    }

    @GetMapping
    public ResponseEntity<List<SeriesResponse>> getAllSeries(){
        return ResponseEntity.ok(seriesService.getAllSeries());
    }

    @DeleteMapping("/{seriesId}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long seriesId){
        seriesService.deleteSeries(seriesId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{seriesId}/posts/{postId}")
    public ResponseEntity<Void> addPostToSeries(@PathVariable Long seriesId, @PathVariable Long postId){
        seriesService.addPostToSeries(seriesId,postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{seriesId}/posts")
    public ResponseEntity<List<PostSeriesResponse>> getAllPostInSeries(@PathVariable Long seriesId){

        return ResponseEntity.ok(seriesService.getAllPostInSeries(seriesId));

    }

    @DeleteMapping("/{seriesId}/posts/{postId}")
    public ResponseEntity<Void> removePostFromSeries(@PathVariable Long seriesId, @PathVariable Long postId){
        seriesService.removePostFromSeries(seriesId,postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{seriesId}/posts/{postId}/navigation")
    public ResponseEntity<SeriesNavigationResponse> getSeriesNavigation(@PathVariable Long seriesId, @PathVariable Long postId){

        return ResponseEntity.ok(seriesService.getNavigation(seriesId,postId));
    }
}
