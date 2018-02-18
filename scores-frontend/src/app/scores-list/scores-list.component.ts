import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {ScoresService} from "./scores.service";
import { Score } from '../score.model';


@Component({
  selector: 'app-scores-list',
  templateUrl: './scores-list.component.html',
  styleUrls: ['./scores-list.component.css']
})
export class ScoresListComponent implements OnInit {

  public scores: Score[];

  constructor(private router: Router, private scoresService : ScoresService) {

  }

  ngOnInit() {
    this.refresh();
    this.scoresService.scoresUpdated.subscribe(
      (scores : Score[]) => {
        this.scores = scores;
      }
    )
  }

  refresh() {
    console.log('Refreshing');
    this.scores = this.scoresService.getScores();
  }

  addScore() {
    this.router.navigate(['/scores', 'new']);
  }
}
