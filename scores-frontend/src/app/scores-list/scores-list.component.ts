import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import {ScoresService} from "./scores.service";
import { Score } from '../score.model';


@Component({
  selector: 'app-scores-list',
  templateUrl: './scores-list.component.html',
  styleUrls: ['./scores-list.component.css']
})
export class ScoresListComponent implements OnInit, OnDestroy {
  public scores: Score[];
  private subscription: Subscription;

  constructor(private router: Router, private scoresService : ScoresService) {

  }

  ngOnInit() {
    this.refresh();
    this.subscription = this.scoresService.scoresUpdated.subscribe(
      (scores : Score[]) => {
        this.scores = scores;
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  refresh() {
    console.log('Refreshing');
    this.scores = this.scoresService.getScores();
  }

  addScore() {
    this.router.navigate(['/scores', 'new']);
  }
}
