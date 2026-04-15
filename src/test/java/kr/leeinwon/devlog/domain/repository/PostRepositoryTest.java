package kr.leeinwon.devlog.domain.repository;

import kr.leeinwon.devlog.domain.post.entity.Post;
import kr.leeinwon.devlog.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void 태그_게시물_함께_조회(){
        List<Post> posts = postRepository.findAllWithTags();
        assertThat(posts).isNotNull();
    }
}
