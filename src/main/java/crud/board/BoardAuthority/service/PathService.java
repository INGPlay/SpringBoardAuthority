package crud.board.BoardAuthority.service;

import crud.board.BoardAuthority.entity.authentication.Path;
import crud.board.BoardAuthority.repository.PathRepository;
import crud.board.BoardAuthority.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PathService {

    private final PathRepository pathRepository;

    public void createPath(String route){
        Path path = makePathObject(route);

        pathRepository.save(path);
    }


    public Path findOrCreatePath(String route){
        Optional<Path> optionalPath = pathRepository.findByRoute(route);

        if (optionalPath.isPresent()){
            return optionalPath.get();
        }

        Path path = makePathObject(route);
        pathRepository.save(path);

        return path;
    }

    public Path makePathObject(String route){
        Path path = new Path();
        path.setRoute(route);

        return path;
    }
}
