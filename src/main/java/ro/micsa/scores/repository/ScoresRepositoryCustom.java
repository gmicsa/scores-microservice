package ro.micsa.scores.repository;

import reactor.core.publisher.Flux;
import ro.micsa.scores.domain.Score;

interface ScoresRepositoryCustom {

    Flux<Score> find(ScoresFilter filter);

}
