import { Component, OnInit, Input } from '@angular/core';
import {Router} from "@angular/router";

import { Score } from '../score.model';
import {ScoresService} from "../scores-list/scores.service";

@Component({
  selector: 'app-score-panel',
  templateUrl: './score-panel.component.html',
  styleUrls: ['./score-panel.component.css']
})
export class ScorePanelComponent implements OnInit {

  @Input() score : Score;
  counter = Array;

  constructor(private router: Router, private scoresService : ScoresService) {

  }

  ngOnInit() {
  }

  removeScore(score: Score) {
    this.scoresService.removeScore(score);
  }

  editScore(score: Score) {
    this.router.navigate(['scores', this.score.id]);
  }
}
