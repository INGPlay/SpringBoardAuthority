package crud.board.BoardAuthority.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import crud.board.BoardAuthority.domain.dto.SearchDto;
import crud.board.BoardAuthority.domain.response.AccountInfoResponse;
import crud.board.BoardAuthority.entity.account.QAccount;
import crud.board.BoardAuthority.repository.custom.AccountRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QAccount account = QAccount.account;
    @Override
    public Page<AccountInfoResponse> search(SearchDto searchDto, Pageable pageable) {
        List<AccountInfoResponse> responses = queryFactory.select(
                        Projections.constructor(
                                AccountInfoResponse.class,
                                account.username,
                                account.role.roleName,
                                account.timeInform
                        )
                )
                .from(account)
                .where(conditionByOption(searchDto))
                .orderBy(account.role.roleName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        /*total Count 쿼리를 직접 날린다.*/
        long total = queryFactory.selectFrom(account)
                .where(conditionByOption(searchDto))
                .fetchCount();

        return new PageImpl<>(responses, pageable, total);
    }

    private BooleanExpression conditionByOption(SearchDto searchDto){
        String searchOption = searchDto.getSearchOption();
        String searchWord = searchDto.getSearchWord();

        if (searchOption == null){
            return null;
        }

        switch (searchOption){
            case "username" :
                return account.username.contains(searchWord);

            case "roleName":
                return account.role.roleName.contains(searchWord);

            default:
                return null;
        }
    }
}
