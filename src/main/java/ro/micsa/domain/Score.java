package ro.micsa.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(level= PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class Score {

    @Id
    String id;

    @NotEmpty(message = "ro.micsa.scores.validation.empty_team1")
    String team1;

    @NotEmpty(message = "ro.micsa.scores.validation.empty_team2")
    String team2;

    @NotNull(message = "ro.micsa.scores.validation.empty_date")
    Date date;

    @Min(value = 0, message = "ro.micsa.scores.validation.negative_goals1")
    byte goals1;

    @Min(value = 0, message = "ro.micsa.scores.validation.negative_goals2")
    byte goals2;

    @Builder
    private Score(String id, String team1, String team2,
                 Date date, byte goals1, byte goals2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.goals1 = goals1;
        this.goals2 = goals2;

        validate(this);
    }

    private void validate(Score score) {
        Set<ConstraintViolation<Score>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(score);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
