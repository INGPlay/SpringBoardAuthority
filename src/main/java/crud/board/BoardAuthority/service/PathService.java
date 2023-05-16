package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.entity.authentication.Path;
import crud.board.BoardAuthority.repository.PathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PathService {

    private final PathRepository pathRepository;

    public void createPath(String route){
        Path path = makePathObject(route);

        pathRepository.save(path);
    }

    public Path makePathObject(String route){
        Path path = new Path();
        path.setRoute(route);

        return path;
    }
}
