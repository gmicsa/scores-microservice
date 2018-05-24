import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http/src/response';
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
    this.subscription = this.scoresService.scoresRemoved.subscribe(
      (score: Score) => {
        console.log('Score removed ' + score.id);

        this.refresh();
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  refresh() {
    console.log('Get scores');
    this.scoresService.getScores().subscribe(
      (scores: Score[]) => {
        this.scores = scores;
      },
      (error: HttpErrorResponse) => {
        console.log('Error loading scores: ' + JSON.stringify(error));
        alert('Error loading scores. Please try again later.');
      }
    );
  }

  addScore() {
    this.router.navigate(['/scores', 'new']);
  }
}
