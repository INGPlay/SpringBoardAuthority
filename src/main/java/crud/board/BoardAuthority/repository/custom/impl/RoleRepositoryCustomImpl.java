package crud.board.BoardAuthority.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import crud.board.BoardAuthority.entity.authentication.QRole;
import crud.board.BoardAuthority.repository.custom.RoleRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QRole role = QRole.role;

    @Override
    public List<String> getRoleNames() {
        return queryFactory.select(role.roleName).distinct()
                .from(role)
                .fetch();
    }
}
