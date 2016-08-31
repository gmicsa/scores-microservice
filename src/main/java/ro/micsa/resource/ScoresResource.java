package ro.micsa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ro.micsa.domain.Score;
import ro.micsa.service.ScoresService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoresResource {

    @Autowired
    private ScoresService scoresService;

    @GetMapping
    @ResponseBody
    public List<Score> getScores(@RequestParam(required = false) String team) {
        return StringUtils.isEmpty(team) ? scoresService.getScores() : scoresService.getScoresForTeam(team);
    }

    @PostMapping
    @ResponseBody
    public Score addScore(@RequestBody @Valid Score score) {
        scoresService.addScore(score);
        return score;
    }

}
