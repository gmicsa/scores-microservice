import {Score} from "../score.model";
import {Subject} from "rxjs/Subject";

export class ScoresService {
  private id : number = 0;

  private scores: Score[] = [
    new Score(this.id++,'FCSB', 3, 'Dinamo', 1, new Date(2018, 1, 7)),
    new Score(this.id++,'Real Madrid', 2, 'Barcelona', 4, new Date(2015, 0, 2)),
    new Score(this.id++,'FC Arges', 0, 'Steaua', 0, new Date(2003, 9, 23)),
    new Score(this.id++,'FC Snagov', 0, 'FCSB', 5, new Date(2017, 7, 12)),
    new Score(this.id++,'FCSB', 7, 'Fulgerul Bragadiru', 1, new Date(2017, 7, 17))
  ];

  public scoresUpdated : Subject<Score[]> = new Subject();

  constructor() {

  }

  getScores() : Score[] {
    return this.scores.slice();
  }

  saveScore(score: Score) {
    if(score.id === null){
      score.id = this.id++;
      this.scores.push(score);
    }else{
      let oldScore : Score = this.scores.filter((s) => s.id === score.id)[0];
      if(oldScore){
        oldScore.date = score.date;
        oldScore.team1 = score.team1;
        oldScore.goals1 = score.goals1;
        oldScore.team2 = score.team2;
        oldScore.goals2 = score.goals2;
      }
    }
    console.log('Save score ' + JSON.stringify(score));

    this.scoresUpdated.next(this.getScores());
  }

  removeScore(score: Score) {
    console.log('Remove ' + JSON.stringify(score));
    let index = this.scores.indexOf(score, 0);
    if (index > -1) {
      this.scores.splice(index, 1);
    }

    this.scoresUpdated.next(this.getScores());
  }

  getScoreById(scoreId: number) : Score {
    return this.scores.find((score) => score.id === scoreId);
  }
}
