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

    public Flux<Score> findAll() {
        return scoresRepository.findAll();
    }

    public Flux<Score> findByTeam(String team) {
        return scoresRepository.findByTeam1OrTeam2(team, team);
    }

    public Mono<Score> findById(String id) {
        return scoresRepository.findById(id);
    }

    public Mono<Score> saveScore(Score score) {
        return scoresRepository.save(score);
    }

    public Mono<Void> deleteScore(String id) {
        return scoresRepository.deleteById(id);
    }
}
