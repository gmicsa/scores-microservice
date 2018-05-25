package ro.micsa.scores.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ro.micsa.scores.domain.Score;
import ro.micsa.scores.domain.ScoreTestBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ro.micsa.scores.domain.ScoreTestBuilder.buildScore;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ScoresRepositoryTest {

    @Autowired
    private ScoresRepository scoresRepository;

    private Score score1;
    private Score score2;

    @Before
    public void setup(){
        Mono<Void> deleteAllMono = scoresRepository.deleteAll();
        StepVerifier.create(deleteAllMono).verifyComplete();

        score1 = buildScore("1986-05-16", "Barcelona", "Steaua", 4, 5);
        score2 = buildScore("2011-12-03", "Steaua", "Dinamo", 4, 0);

        Flux<Score> insertedScoresFlux = scoresRepository.saveAll(List.of(score1, score2));
        StepVerifier.create(insertedScoresFlux).expectNextCount(2).verifyComplete();
    }

    @Test
    public void find_noFiler() {
        List<Score> scores = scoresRepository.find(null).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2, score1);
    }

    @Test
    public void find_emptyFilter() {
        List<Score> scores = scoresRepository.find(new ScoresFilter()).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2, score1);
    }

    @Test
    public void find_filterByTeamMatching2Scores_scoresAreSortedByDateDesc() {
        ScoresFilter filter = new ScoresFilter();
        filter.setTeam(Optional.of("Steaua"));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2, score1);
    }

    @Test
    public void find_filterByTeamMatching1Score() {
        ScoresFilter filter = new ScoresFilter();
        filter.setTeam(Optional.of("Dinamo"));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2);
    }

    @Test
    public void find_filterByFrom() {
        ScoresFilter filter = new ScoresFilter();
        filter.setFrom(Optional.of(LocalDate.parse("2010-01-01")));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2);
    }

    @Test
    public void find_filterByUntil() {
        ScoresFilter filter = new ScoresFilter();
        filter.setUntil(Optional.of(LocalDate.parse("2010-01-01")));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).containsExactly(score1);
    }

    @Test
    public void find_filterByFromUntilAndTeam() {
        ScoresFilter filter = new ScoresFilter();
        filter.setFrom(Optional.of(LocalDate.parse("2010-01-01")));
        filter.setUntil(Optional.of(LocalDate.parse("2015-01-01")));
        filter.setTeam(Optional.of("Steaua"));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2);
    }
    
    @Test
    public void find_filterByFromUntilAndTeam_noScoreMatched() {
        ScoresFilter filter = new ScoresFilter();
        filter.setFrom(Optional.of(LocalDate.parse("2010-01-01")));
        filter.setUntil(Optional.of(LocalDate.parse("2015-01-01")));
        filter.setTeam(Optional.of("Barcelona"));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).isEmpty();
    }
    
    @Test
    public void find_filterByFromUntil() {
        ScoresFilter filter = new ScoresFilter();
        filter.setFrom(Optional.of(LocalDate.parse("2010-01-01")));
        filter.setUntil(Optional.of(LocalDate.parse("2015-01-01")));

        List<Score> scores = scoresRepository.find(filter).collectList().block();

        Assertions.assertThat(scores).containsExactly(score2);
    }
}