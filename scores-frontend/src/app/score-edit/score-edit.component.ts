import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from "@angular/router";

import { Score } from '../score.model';
import { ScoresService } from '../scores-list/scores.service';

@Component({
  selector: 'app-score-edit',
  templateUrl: './score-edit.component.html',
  styleUrls: ['./score-edit.component.css'],
})
export class ScoreEditComponent implements OnInit {
  public mode: string = 'new';
  public score: Score;
  private scoreId: string;

  constructor(private activatedRoute : ActivatedRoute,
    private router: Router,
    private scoresService: ScoresService) { }

  ngOnInit() {
    this.score = new Score(null, '', 0, '', 0, new Date());

    this.activatedRoute.params.subscribe(
      (params : Params) => {
        if('new' === params['id']){
          this.mode = 'new';
        } else {
          this.mode = "edit";
          this.scoreId = params['id'];

          this.scoresService.getScoreById(this.scoreId).subscribe(
            (score: Score) => {
              this.score = score;
              },
            (error: any) => {
              console.log('Not able to load score with ID: ' + this.scoreId + '\n' + JSON.stringify(error));
              alert('Not able to load score!');
            });
        }
      }
    )
  }

  onSubmit(){
    this.score.date.setMinutes(-this.score.date.getTimezoneOffset());// add GMT offset so that during serialization previous day is not sent to backend

    this.scoresService.saveScore(this.score).subscribe(
      (score: Score) => {
        console.log('Score saved ' + JSON.stringify(score));
        this.router.navigate(['/scores']);
      },
      (error: any) => {
        console.log('Not able to save score: ' + JSON.stringify(error));
        alert('Not able to save score!');
      }
    );
  }

  onCancel(){
    this.router.navigate(['/scores']);
  }
}
