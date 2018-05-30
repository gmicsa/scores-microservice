
import {map} from 'rxjs/operators';
import {Score, ScoreFilter} from "../score.model";
import {Observable, Subject} from "rxjs";
import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {DatePipe} from "@angular/common";

@Injectable()
export class ScoresService {
  public scoresRemoved : Subject<Score> = new Subject();

  constructor(private httpClient: HttpClient) {}

  getScores(filter: ScoreFilter) : Observable<Score[]> {
    let params = new HttpParams();

    if(filter.team){
      params = params.append('team', filter.team);
    }
    if(filter.from) {
      params = params.append('from', this.formatDate(filter.from));
    }
    if(filter.until) {
      params = params.append('until', this.formatDate(filter.until));
    }

    return this.httpClient.get<Score[]>(this.getScoresApiBaseURL(), {params: params}).pipe(
      map((scores: Score[]) => {
        scores.forEach((score: Score) => {
          score.date = new Date(score.date); //map string to date
        });
        return scores;
      }));
  }

  private formatDate(date: Date): string {
    let pipe = new DatePipe('en-GB');
    return pipe.transform(date, 'yyyy-MM-dd');
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

    return this.httpClient.get<Score>(this.getScoresApiBaseURL() + scoreId).pipe(
      map((score: Score) => {
        score.date = new Date(score.date);  //map string to date
        return score;
    }));
  }

  private getScoresApiBaseURL() {
    return 'http://' + location.host + '/api/scores/';
  }
}
