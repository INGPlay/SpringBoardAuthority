package crud.board.BoardAuthority.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class Config {
    private final EntityManager em;

    @Bean
    public JPAQueryFactory japQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
