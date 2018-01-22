package ro.micsa.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Score {

    @Id
    private String id;
    @NotEmpty(message = "ro.micsa.scores.validation.empty_team1")
    private String team1;
    @NotEmpty(message = "ro.micsa.scores.validation.empty_team2")
    private String team2;
    @NotNull(message = "ro.micsa.scores.validation.empty_date")
    private Date date;
    @Min(value = 0, message = "ro.micsa.scores.validation.negative_goals1")
    private byte goals1;
    @Min(value = 0, message = "ro.micsa.scores.validation.negative_goals2")
    private byte goals2;

    private Score() {
    }

    private Score(Builder builder) {
        this.team1 = builder.team1;
        this.team2 = builder.team2;
        this.date = builder.date;
        this.goals1 = builder.goals1;
        this.goals2 = builder.goals2;
    }

    public String getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Date getDate() {
        return date;
    }

    public byte getGoals1() {
        return goals1;
    }

    public byte getGoals2() {
        return goals2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return goals1 == score.goals1 &&
                goals2 == score.goals2 &&
                Objects.equals(id, score.id) &&
                Objects.equals(team1, score.team1) &&
                Objects.equals(team2, score.team2) &&
                Objects.equals(date, score.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, team1, team2, date, goals1, goals2);
    }

    @Override
    public String toString() {
        return "Score{" +
                "id='" + id + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", date=" + date +
                ", goals1=" + goals1 +
                ", goals2=" + goals2 +
                '}';
    }

    public static class Builder {
        private String team1;
        private String team2;
        private Date date;
        private byte goals1;
        private byte goals2;

        public Builder team1(String team1) {
            this.team1 = team1;
            return this;
        }

        public Builder team2(String team2) {
            this.team2 = team2;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder goals1(byte goals1) {
            this.goals1 = goals1;
            return this;
        }

        public Builder goals2(byte goals2) {
            this.goals2 = goals2;
            return this;
        }

        public Builder fromPrototype(Score prototype) {
            team1 = prototype.team1;
            team2 = prototype.team2;
            date = prototype.date;
            goals1 = prototype.goals1;
            goals2 = prototype.goals2;
            return this;
        }

        public Score build() {
            Score score = new Score(this);

            validate(score);

            return score;
        }

        private void validate(Score score) {
            Set<ConstraintViolation<Score>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(score);
            if (!constraintViolations.isEmpty()) {
                throw new ConstraintViolationException(constraintViolations);
            }
        }
    }
}
