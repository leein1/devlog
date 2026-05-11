package kr.leeinwon.devlog.domain.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.leeinwon.devlog.domain.tag.QPostTag;
import kr.leeinwon.devlog.domain.tag.QTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> searchPosts(Long cusor, int size, Long categoryId, String tagName, String keyword) {

        QPost post = QPost.post;
        QPostTag postTag = QPostTag.postTag;
        QTag tag = QTag.tag;

        BooleanBuilder builder = new BooleanBuilder();

        if(cusor != null){
            builder.and(post.id.lt(cusor));
        };
        if(categoryId != null){
            builder.and(post.category.id.eq(categoryId));
        }
        if(keyword != null && !keyword.isBlank()){
            builder.and(post.title.contains(keyword));
        }

        var query = jpaQueryFactory
                .selectDistinct(post)
                .from(post)
                .leftJoin(post.category).fetchJoin()
                .where(builder)
                .orderBy(post.id.desc())
                .limit(size);

        if(tagName != null && !tagName.isBlank()){
            query = query
                    .join(post.postTags, postTag)
                    .join(postTag.tag, tag)
                    .where(tag.name.eq(tagName));
        }

        return query.fetch();
    }
}
