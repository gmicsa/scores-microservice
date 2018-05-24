package ro.micsa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.micsa.domain.Score;
import ro.micsa.service.ScoresService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/scores")
@Slf4j
public class ScoresResource {

    @Autowired
    private ScoresService scoresService;

    @GetMapping
    @ResponseBody
    public Flux<Score> find(@RequestParam(required = false) String team) {
        log.info("Find scores" + (team != null ? " for team " + team : ""));

        return StringUtils.isEmpty(team) ? scoresService.findAll() : scoresService.findByTeam(team);
    }

    @GetMapping("/{scoreId}")
    public Mono<Score> findById(@PathVariable @NotNull String scoreId) {
        log.info("Find score by id " + scoreId);

        return scoresService.findById(scoreId);
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
