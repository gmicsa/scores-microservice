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
  private mode: string = 'new';
  private scoreId: number;
  private score: Score;

  constructor(private activatedRoute : ActivatedRoute, 
    private router: Router,
    private scoresService: ScoresService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      (params : Params) => {
        if('new' === params['id']){
          this.mode = 'new';
          this.score = new Score(null, '', 0, '', 0, new Date());
        } else {
          this.mode = "edit";
          this.scoreId = +params['id'];
          this.score =  JSON.parse(JSON.stringify(this.scoresService.getScoreById(this.scoreId)));;
        }
      }
    )
  }

  onSubmit(){
    this.scoresService.saveScore(this.score);
    this.router.navigate(['/scores']);
  }

  onCancel(){
    this.router.navigate(['/scores']);
  }
}
