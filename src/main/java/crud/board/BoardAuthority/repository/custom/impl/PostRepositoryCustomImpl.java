package crud.board.BoardAuthority.repository.custom.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.entity.thread.Post;
import crud.board.BoardAuthority.entity.thread.QPost;
import crud.board.BoardAuthority.repository.custom.PostRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final QPost post = QPost.post;
    @Override
    public Page<Post> search(SearchDto searchDto, Pageable pageable) {

        List<Post> posts = queryFactory.selectFrom(post)
                .where(conditionByOption(searchDto))
                .orderBy(post.timeInform.createdTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        /*total Count 쿼리를 직접 날린다.*/
        long total = queryFactory.selectFrom(post)
                .where(conditionByOption(searchDto))
                .fetchCount();
        
        return new PageImpl<>(posts, pageable, total);
    }

    private BooleanExpression conditionByOption(SearchDto searchDto){
        String searchOption = searchDto.getSearchOption();
        String searchWord = searchDto.getSearchWord();

        if (searchOption == null){
            return null;
        }

        switch (searchOption){
            case "title" :
                return post.title.contains(searchWord);

            case "content":
                return post.content.contains(searchWord);

            case "author" :
                return post.account.username.contains(searchWord);

            default:
                return null;
        }
    }
}
