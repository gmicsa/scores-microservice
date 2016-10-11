import {Component, OnInit} from "@angular/core";
import {ScoresService} from "./app.service";
import {Score} from "./score";

@Component({
    selector: 'scores-app',
    template: `<h1>{{title}}</h1>
    <h2>Today matches:</h2>
    <div *ngIf="error" class="error">
        An error occurred: {{error.status}}
    </div>
    <div *ngIf="!error">
        <div *ngFor="let score of scores">
          <span [class.winner]="score.team1Winner()">{{score.team1}}</span>
          <span>{{score.goals1}}</span>
          -
          <span>{{score.goals2}}</span>
          <span [class.winner]="score.team2Winner()">{{score.team2}}</span>
        </div>
    </div>
    <br/>
    <button md-raised-button class="md-raised md-primary" (click)="reload()">Reload</button>
    <br/>
    `,
    providers: [ScoresService]
})
export class AppComponent implements OnInit {
    title = 'Football scores';
    scores: Score[];
    error: any;

    constructor(private scoresService: ScoresService) {
    }

    ngOnInit(): void {
        this.getScores();
    }

    reload(): void {
        this.getScores();
    }

    getScores(): void {
        this.scoresService.getScores().then(
            scores => {
                this.scores = scores;
                this.error = null;
            },
            error => {
                this.error = error;
                this.scores = [];
            });
    }
}
