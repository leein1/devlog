package kr.leeinwon.devlog.domain.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.leeinwon.devlog.domain.tag.PostTagRepository;
import kr.leeinwon.devlog.domain.tag.QPostTag;
import kr.leeinwon.devlog.domain.tag.QTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final PostTagRepository postTagRepository;

    @Override
    public List<Post> searchPostsOld(Long cursor, int size, Long categoryId, String tagName, String keyword) {

        QPost post = QPost.post;
        QPostTag postTag = QPostTag.postTag;
        QTag tag = QTag.tag;

        BooleanBuilder builder = new BooleanBuilder();

        if (cursor != null) {
            builder.and(post.id.lt(cursor));
        }
        ;
        if (categoryId != null) {
            builder.and(post.category.id.eq(categoryId));
        }
        if (keyword != null && !keyword.isBlank()) {
            builder.and(post.title.contains(keyword));
        }

        var query = jpaQueryFactory
                .selectDistinct(post)
                .from(post)
                .leftJoin(post.category).fetchJoin()
                .where(builder)
                .orderBy(post.id.desc())
                .limit(size);

        if (tagName != null && !tagName.isBlank()) {
            query = query
                    .join(post.postTags, postTag)
                    .join(postTag.tag, tag)
                    .where(tag.name.eq(tagName));
        }

        return query.fetch();
    }

    @Override
    public Page<Post> searchPosts(Pageable pageable, Long categoryId, String tagName, String keyword) {
        QPost post = QPost.post;
        QPostTag postTag = QPostTag.postTag;
        QTag tag = QTag.tag;

        BooleanBuilder builder = new BooleanBuilder();

        if (categoryId != null) {
            builder.and(post.category.id.eq(categoryId));
        }
        if (keyword != null && !keyword.isBlank()) {
            builder.and(post.title.contains(keyword));
        }

        var query = jpaQueryFactory
                .selectDistinct(post)
                .from(post)
                .leftJoin(post.category).fetchJoin()
                .where(builder)
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (tagName != null && !tagName.isBlank()) {
            query = query
                    .join(post.postTags, postTag)
                    .join(postTag.tag, tag)
                    .where(tag.name.eq(tagName));
        }

        List<Post> content = query.fetch();

        var countQuery = jpaQueryFactory
                .select(post.countDistinct())
                .from(post)
                .where(builder);

        if (tagName != null && !tagName.isBlank()) {
            countQuery = countQuery
                    .join(post.postTags, postTag)
                    .join(postTag.tag, tag)
                    .where(tag.name.eq(tagName));
        }

        long total = countQuery.fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


    //  N _+ 1  발생지점
    // Category Join 존재하지 않음. searchPosts(), searchPostOld() 엔 leftJoin(...).fetchJoin() 존재
    @Override
    public List<Post> findNearbyPosts(Long id, int n) {

        QPost post = QPost.post;

        List<Post> before = jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.category).fetchJoin()
                .where(post.id.lt(id))
                .orderBy(post.id.desc())
                .limit(n)
                .fetch();

        List<Post> after = jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.category).fetchJoin()
                .where(post.id.gt(id))
                .orderBy(post.id.asc())
                .limit(n)
                .fetch();

        Collections.reverse(before);
        before.addAll(after);

        return before;
    }
}
