package ro.micsa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.micsa.domain.Score;
import ro.micsa.service.ScoresService;

import javax.validation.Valid;

@RestController
@RequestMapping("/scores")
@Slf4j
public class ScoresResource {

    @Autowired
    private ScoresService scoresService;

    @GetMapping
    @ResponseBody
    public Flux<Score> getScores(@RequestParam(required = false) String team) {
        log.info("Get scores" + (team != null ? " for team " + team : ""));

        return StringUtils.isEmpty(team) ? scoresService.getScores() : scoresService.getScoresForTeam(team);
    }

    @PostMapping
    @ResponseBody
    public Mono<Score> addScore(@RequestBody @Valid Score score) {
        log.info("Add score " + score);

        return scoresService.addScore(score);
    }

}
