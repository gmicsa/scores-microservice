package ro.micsa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.micsa.domain.Score;
import ro.micsa.repository.ScoresRepository;

import java.util.List;

@Service
public class ScoresService {

    @Autowired
    private ScoresRepository scoresRepository;

    public List<Score> getScores() {
        return scoresRepository.findAll();
    }

    public List<Score> getScoresForTeam(String team) {
        return scoresRepository.findByTeam1OrTeam2(team, team);
    }

    public synchronized Score addScore(Score score) {
        return scoresRepository.save(score);
    }
}
