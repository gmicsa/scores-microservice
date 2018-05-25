package ro.micsa.scores.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ro.micsa.scores.domain.Score;

public interface ScoresRepository extends ReactiveMongoRepository<Score, String>, ScoresRepositoryCustom {

}
