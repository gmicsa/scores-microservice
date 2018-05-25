package ro.micsa.scores.domain;

import java.time.LocalDate;

public class ScoreTestBuilder {

    private String team1 = "Steaua";
    private String team2 = "Dinamo";
    private String date = "2018-05-25";
    private int goals1 = 3;
    private int goals2  = 2;

    private ScoreTestBuilder() {
    }

    public static ScoreTestBuilder builder(){
        return new ScoreTestBuilder();
    }

    public ScoreTestBuilder team1(String team1){
        this.team1 = team1;
        return this;
    }

    public ScoreTestBuilder team2(String team2){
        this.team2 = team2;
        return this;
    }

    public ScoreTestBuilder date(String date){
        this.date = date;
        return this;
    }

    public ScoreTestBuilder goals1(int goals1){
        this.goals1 = goals1;
        return this;
    }

    public ScoreTestBuilder goals2(int goals2){
        this.goals2 = goals2;
        return this;
    }

    public static Score buildScore(String team1, String team2, int goals1, int goals2) {
        return ScoreTestBuilder.builder()
                .team1(team1)
                .team2(team2)
                .goals1(goals1)
                .goals2(goals2)
                .build();
    }

    public static Score buildScore(String date, String team1, String team2, int goals1, int goals2) {
        return ScoreTestBuilder.builder()
                .team1(team1)
                .team2(team2)
                .date(date)
                .goals1(goals1)
                .goals2(goals2)
                .build();
    }

    public Score build(){
        return Score.builder()
                .team1(team1)
                .team2(team2)
                .date(LocalDate.parse(date))
                .goals1((byte) goals1)
                .goals2((byte) goals2)
                .build();
    }
}
