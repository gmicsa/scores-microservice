package ro.micsa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.micsa.domain.Score;
import ro.micsa.service.ScoresService;

import javax.validation.Valid;

@RestController
@RequestMapping("/scores")
public class ScoresResource {

    @Autowired
    private ScoresService scoresService;

    @GetMapping
    @ResponseBody
    public Flux<Score> getScores(@RequestParam(required = false) String team) {
        return StringUtils.isEmpty(team) ? scoresService.getScores() : scoresService.getScoresForTeam(team);
    }

    @PostMapping
    @ResponseBody
    public Mono<Score> addScore(@RequestBody @Valid Score score) {
        return scoresService.addScore(score);
    }

}
