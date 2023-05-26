package crud.board.BoardAuthority.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import crud.board.BoardAuthority.entity.authentication.QPath;
import crud.board.BoardAuthority.entity.authentication.QRole;
import crud.board.BoardAuthority.repository.custom.RoleRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QRole role = QRole.role;
    private final QPath path = QPath.path;

    @Override
    public List<String> getRoleNames() {
        return queryFactory.select(role.roleName).distinct()
                .from(role)
                .fetch();
    }

    @Override
    public List<String> getRoutes(String roleName) {

        return queryFactory.select(path.route)
                .from(role)
                .join(role.paths, path)
                .where(role.roleName.eq(roleName))
                .fetch();
    }
}
