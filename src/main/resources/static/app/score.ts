export class Score {
    id: string;
    team1: string;
    goals1: number;
    team2: string;
    goals2: number;
    date: number;


    constructor(item: Object) {
        Object.assign(this, item);
    }

    tie(): boolean {
        return this.goals1 === this.goals2;
    }

    team1Winner(): boolean {
        return this.goals1 > this.goals2;
    }

    team2Winner(): boolean {
        return this.goals1 < this.goals2;
    }
}
