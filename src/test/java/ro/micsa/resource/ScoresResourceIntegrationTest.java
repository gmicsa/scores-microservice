package ro.micsa.resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ro.micsa.domain.Score;
import ro.micsa.repository.ScoresRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ScoresResourceIntegrationTest {

    public static final String SCORES_BASE_URL = "http://localhost:8080/scores";
    @Autowired
    private ScoresRepository scoresRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void deleteAllScores() {
        scoresRepository.deleteAll();
    }

    @Test
    public void given2ScoresPersisted_whenFilteringScoresByOneTeam_thenRightScoreIsReturned() {
        Score scoreBarcaSteaua = buildScore("Barcelona", "Steaua", 3, 2);

        Score scoreDinamoForesta = buildScore("Dinamo", "Foresta", 4, 5);

        scoresRepository.saveAll(List.of(scoreBarcaSteaua, scoreDinamoForesta)).blockLast();

        ResponseEntity<List<Score>> response = restTemplate
                .exchange(SCORES_BASE_URL + "?team=Steaua", GET, null, new ParameterizedTypeReference<List<Score>>(){});

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).containsOnly(scoreBarcaSteaua);
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