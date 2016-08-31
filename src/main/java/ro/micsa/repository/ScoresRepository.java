package ro.micsa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.micsa.domain.Score;

import java.util.List;

public interface ScoresRepository extends MongoRepository<Score, String> {

    List<Score> findByTeam1OrTeam2(String team1, String team2);

}
