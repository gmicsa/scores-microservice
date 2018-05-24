import {Score} from "../score.model";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ScoresService {
  public scoresRemoved : Subject<Score> = new Subject();

  constructor(private httpClient: HttpClient) {}

  getScores() : Observable<Score[]> {
    return this.httpClient.get<Score[]>(this.getScoresApiBaseURL())
      .map((scores: Score[]) => {
        scores.forEach((score: Score) => {
          score.date = new Date(score.date); //map string to date
        });
        return scores;
      });
  }

  saveScore(score: Score): Observable<Score> {
    console.log('Save ' + JSON.stringify(score));

    if(score.id === null){
      return this.httpClient.post<Score>(this.getScoresApiBaseURL(), score);
    }else{
      return this.httpClient.put<Score>(this.getScoresApiBaseURL() + score.id, score);
    }
  }

  removeScore(score: Score) {
    console.log('Remove ' + JSON.stringify(score));

    return this.httpClient.delete(this.getScoresApiBaseURL() + score.id).subscribe(
      () => this.scoresRemoved.next(score),
      () => alert('Not able to remove score!')
    );
  }

  getScoreById(scoreId: string) : Observable<Score> {
    console.log('Get score with id ' + scoreId);

    return this.httpClient.get<Score>(this.getScoresApiBaseURL() + scoreId)
      .map((score: Score) => {
        score.date = new Date(score.date);  //map string to date
        return score;
    });
  }

  private getScoresApiBaseURL() {
    return 'http://' + location.host + '/api/scores/';
  }
}
