import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import "rxjs/add/operator/toPromise";
import {Score} from "./score";

@Injectable()
export class ScoresService {
    private url = 'scores';

    constructor(private http: Http) {
    }

    getScores(): Promise<Score[]> {
        console.log('Get scores...');
        return this.http.get(this.url)
            .toPromise()
            .then(response => response.json().map(item => new Score(item)))
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}