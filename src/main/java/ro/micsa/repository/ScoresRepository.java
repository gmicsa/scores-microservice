package ro.micsa.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ro.micsa.domain.Score;

public interface ScoresRepository extends ReactiveMongoRepository<Score, String> {

    Flux<Score> findByTeam1OrTeam2(String team1, String team2);

}
