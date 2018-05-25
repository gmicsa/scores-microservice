package ro.micsa.scores.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.micsa.scores.domain.Score;
import ro.micsa.scores.repository.ScoresFilter;
import ro.micsa.scores.repository.ScoresRepository;

@Service
public class ScoresService {

    @Autowired
    private ScoresRepository scoresRepository;

    public Flux<Score> find(ScoresFilter filter) {
        return scoresRepository.find(filter);
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
