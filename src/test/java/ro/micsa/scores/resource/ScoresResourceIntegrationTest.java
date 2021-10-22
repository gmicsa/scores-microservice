package ro.micsa.scores.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ro.micsa.scores.domain.Score;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.micsa.scores.domain.ScoreTestBuilder.buildScore;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ScoresResourceIntegrationTest {

    private static final String SCORES_BASE_URL = "http://localhost:8080/api/scores";
    private static final WebClient webClient = WebClient.create(SCORES_BASE_URL);

    @Autowired
    private ReactiveMongoOperations mongo;


    @BeforeEach
    public void deleteAllScores() {
        Mono<Void> dropResult = mongo.dropCollection(Score.class);
        StepVerifier.create(dropResult).verifyComplete();
    }

    @Test
    public void given2ScoresPersisted_whenFindScoresByOneTeam_thenRightScoreIsReturned() {
        Score scoreBarcaSteaua = buildScore("Barcelona", "Steaua", 3, 2);

        Score scoreDinamoForesta = buildScore("Dinamo", "Foresta", 4, 5);

        Flux<Score> insertedScores = mongo.insertAll(List.of(scoreBarcaSteaua, scoreDinamoForesta));
        StepVerifier.create(insertedScores).expectNextCount(2).verifyComplete();

        ResponseEntity<List<Score>> response = webClient
                .get()
                .uri(builder -> builder.queryParam("team", "Steaua").build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Score>>() {
                })
                .block();


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsOnly(scoreBarcaSteaua);
    }

    @Test
    public void saveScore() {
        Score scoreToBeCreated = buildScore("Team1", "Team2", 1, 0);

        ResponseEntity<Score> response = webClient
                .post()
                .body(Mono.just(scoreToBeCreated), Score.class)
                .retrieve()
                .toEntity(Score.class)
                .block();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(scoreToBeCreated);
    }

    @Test
    public void updateScore() {
        Score scoreToSaveInDb = buildScore("Barcelona", "Steaua", 3, 2);
        Score scoreFromDb = mongo.insert(scoreToSaveInDb).block();

        Score updatedScore = Score
                .builder()
                .id(scoreFromDb.getId())
                .team1(scoreFromDb.getTeam1())
                .team2(scoreFromDb.getTeam2())
                .goals1(scoreFromDb.getGoals1())
                .goals2((byte) 0)
                .date(scoreFromDb.getDate())
                .build();

        ResponseEntity<Score> response = webClient
                .put()
                .uri(URI.create(SCORES_BASE_URL + "/" + scoreFromDb.getId()))
                .body(Mono.just(updatedScore), Score.class)
                .retrieve()
                .toEntity(Score.class)
                .block();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedScore);
    }

    @Test
    public void findById() {
        Score scoreToSaveInDb = buildScore("Barcelona", "Steaua", 3, 2);
        Score scoreFromDb = mongo.insert(scoreToSaveInDb).block();

        ResponseEntity<Score> response = webClient
                .get()
                .uri(URI.create(SCORES_BASE_URL + "/" + scoreFromDb.getId()))
                .retrieve()
                .toEntity(Score.class)
                .block();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(scoreFromDb);
    }

    @Test
    public void givenOneScoreInDb_whenDeletingScore_noScoreRemainsInDb() {
        Score scoreToSaveInDb = buildScore("Barcelona", "Steaua", 3, 2);
        Score scoreFromDb = mongo.insert(scoreToSaveInDb).block();

        ResponseEntity<Void> response = webClient
                .delete()
                .uri(URI.create(SCORES_BASE_URL + "/" + scoreFromDb.getId()))
                .retrieve()
                .toEntity(Void.class)
                .block();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Flux<Score> allScores = mongo.findAll(Score.class);
        StepVerifier.create(allScores).expectComplete();
    }
}