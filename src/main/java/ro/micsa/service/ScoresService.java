package ro.micsa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.micsa.domain.Score;
import ro.micsa.repository.ScoresRepository;

@Service
public class ScoresService {

    @Autowired
    private ScoresRepository scoresRepository;

    public Flux<Score> getScores() {
        return scoresRepository.findAll();
    }

    public Flux<Score> getScoresForTeam(String team) {
        return scoresRepository.findByTeam1OrTeam2(team, team);
    }

    public synchronized Mono<Score> addScore(Score score) {
        return scoresRepository.save(score);
    }
}
