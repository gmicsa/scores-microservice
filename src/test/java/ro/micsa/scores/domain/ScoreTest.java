package ro.micsa.scores.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreTest {

    @Test
    public void emptyFields() {
        assertThrows(ConstraintViolationException.class, () -> Score.builder().build());
    }

    @Test
    public void negativeScore() {
        final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> Score.builder()
                        .team1("Barcelona")
                        .team2("Steaua")
                        .date(LocalDate.now())
                        .goals1((byte) 3)
                        .goals2((byte) -1)
                        .build());

        assertThat(exception.getConstraintViolations().stream().findFirst().get().getMessage(),
                equalTo("ro.micsa.scores.validation.negative_goals2"));
    }

}