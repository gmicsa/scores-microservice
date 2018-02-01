package ro.micsa.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ro.micsa.domain.Score;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ScoresResourceIntegrationTest {

    private static final String SCORES_BASE_URL = "http://localhost:8080/scores";

    private static WebClient webClient = WebClient.create(SCORES_BASE_URL);

    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;


    @Before
    public void deleteAllScores() {
        Mono<Void> dropResult = reactiveMongoOperations.dropCollection(Score.class);
        StepVerifier.create(dropResult).verifyComplete();
    }

    @Test
    public void given2ScoresPersisted_whenFindScoresByOneTeam_thenRightScoreIsReturned() {
        Score scoreBarcaSteaua = buildScore("Barcelona", "Steaua", 3, 2);

        Score scoreDinamoForesta = buildScore("Dinamo", "Foresta", 4, 5);

        Flux<Score> insertedScores = reactiveMongoOperations.insertAll(List.of(scoreBarcaSteaua, scoreDinamoForesta));
        StepVerifier.create(insertedScores).expectNextCount(2).verifyComplete();

        ClientResponse response = webClient
                .get()
                .uri(builder -> builder.queryParam("team", "Steaua").build())
                .exchange()
                .block();
        List<Score> scores = response.bodyToMono(new ParameterizedTypeReference<List<Score>>(){}).block();


        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(scores).containsOnly(scoreBarcaSteaua);
    }

    @Test
    public void saveScore() {
        Score scoreToBeCreated = buildScore("Team1", "Team2", 1, 0);

        ClientResponse response = webClient
                .post()
                .body(Mono.just(scoreToBeCreated), Score.class)
                .exchange()
                .block();

        Score createdScore = response.bodyToMono(Score.class).block();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdScore.getId()).isNotNull();
        assertThat(createdScore).isEqualToIgnoringGivenFields(scoreToBeCreated, "id");
    }

    @Test
    public void updateScore() {
        Score scoreToSaveInDb = buildScore("Barcelona", "Steaua", 3, 2);
        Score scoreFromDb = reactiveMongoOperations.insert(scoreToSaveInDb).block();

        Score updatedScore = Score
                .builder()
                .id(scoreFromDb.getId())
                .team1(scoreFromDb.getTeam1())
                .team2(scoreFromDb.getTeam2())
                .goals1(scoreFromDb.getGoals1())
                .goals2((byte) 0)
                .date(scoreFromDb.getDate())
                .build();

        ClientResponse response = webClient
                .put()
                .uri(URI.create(SCORES_BASE_URL + "/" + scoreFromDb.getId()))
                .body(Mono.just(updatedScore), Score.class)
                .exchange()
                .block();
        Score updatedScoreResponse = response.bodyToMono(Score.class).block();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedScoreResponse).isEqualTo(updatedScore);
    }

    @Test
    public void findById() {
        Score scoreToSaveInDb = buildScore("Barcelona", "Steaua", 3, 2);
        Score scoreFromDb = reactiveMongoOperations.insert(scoreToSaveInDb).block();

        ClientResponse response = webClient
                .get()
                .uri(URI.create(SCORES_BASE_URL + "/" + scoreFromDb.getId()))
                .exchange()
                .block();

        Score scoreResponse = response.bodyToMono(Score.class).block();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
        assertThat(scoreResponse).isEqualTo(scoreFromDb);
    }

    @Test
    public void givenOneScoreInDb_whenDeletingScore_noScoreRemainsInDb() {
        Score scoreToSaveInDb = buildScore("Barcelona", "Steaua", 3, 2);
        Score scoreFromDb = reactiveMongoOperations.insert(scoreToSaveInDb).block();

        ClientResponse response = webClient
                .delete()
                .uri(URI.create(SCORES_BASE_URL + "/" + scoreFromDb.getId()))
                .exchange()
                .block();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);

        Flux<Score> allScores = reactiveMongoOperations.findAll(Score.class);
        StepVerifier.create(allScores).expectComplete();
    }

    private Score buildScore(String team1, String team2, int score1, int score2) {
        return Score.builder()
                .team1(team1)
                .team2(team2)
                .date(LocalDate.now())
                .goals1((byte) score1)
                .goals2((byte) score2)
                .build();
    }
}