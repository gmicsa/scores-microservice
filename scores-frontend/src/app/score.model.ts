export class Score {
    constructor(public id: string,
                public team1: string,
                public goals1: number,
                public team2: string,
                public goals2: number,
                public date: Date) {}
}

export class ScoreFilter {
    constructor(public team: string,
                public from: Date,
                public until: Date) {}
}
