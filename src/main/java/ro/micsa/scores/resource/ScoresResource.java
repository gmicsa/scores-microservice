package ro.micsa.scores.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.micsa.scores.domain.Score;
import ro.micsa.scores.repository.ScoresFilter;
import ro.micsa.scores.service.ScoresService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/scores")
@Slf4j
public class ScoresResource {

    @Autowired
    private ScoresService scoresService;

    @GetMapping
    @ResponseBody
    public Flux<Score> find(@RequestParam(required = false) String team,
                            @RequestParam(required = false) String from,
                            @RequestParam(required = false) String until) {
        ScoresFilter filter = new ScoresFilter();
        filter.setTeam(Optional.ofNullable(team));
        filter.setFrom(Optional.ofNullable(from).flatMap(date -> Optional.of(LocalDate.parse(from))));
        filter.setUntil(Optional.ofNullable(until).flatMap(date -> Optional.of(LocalDate.parse(until))));

        log.info("Find scores for filter " + filter);

        return scoresService.find(filter);
    }

    @GetMapping("/{scoreId}")
    public Mono<Score> findById(@PathVariable @NotNull String scoreId) {
        log.info("Find score by id " + scoreId);

        return scoresService
                .findById(scoreId)
                .switchIfEmpty(Mono.error(new RuntimeException("Score with id " + scoreId + " not found")));
    }

    @PostMapping
    @ResponseBody
    public Mono<Score> addScore(@RequestBody @NotNull @Valid Score score) {
        log.info("Add score " + score);

        return scoresService.saveScore(score);
    }

    @PutMapping("/{scoreId}")
    @ResponseBody
    public Mono<Score> updateScore(@PathVariable @NotNull String scoreId, @RequestBody @NotNull @Valid Score score) {
        Assert.isTrue(scoreId.equals(score.getId()), "Score Id path param does not match score id from body");

        log.info("Update score " + score);

        return scoresService.saveScore(score);
    }

    @DeleteMapping("/{scoreId}")
    public Mono<Void> deleteScore(@PathVariable @NotNull String scoreId) {
        log.info("Delete score " + scoreId);

        return scoresService.deleteScore(scoreId);
    }
}
