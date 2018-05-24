package ro.micsa.domain;

import org.hamcrest.CustomMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Date;

//TODO upgrade to JUnit5 late 2016
public class ScoreTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = ConstraintViolationException.class)
    public void emptyFields() {
        Score.builder().build();
    }

    @Test
    public void negativeScore() {
        expectedException.expect(ConstraintViolationException.class);
        expectedException.expect(new CustomMatcher<ConstraintViolationException>("negative_goals2") {
            @Override
            public boolean matches(Object item) {
                ConstraintViolation<?> firstConstraintViolation = ((ConstraintViolationException) item).getConstraintViolations().stream().findFirst().get();
                return firstConstraintViolation.getMessage().equals("ro.micsa.scores.validation.negative_goals2");
            }
        });

        Score.builder()
                .team1("Barcelona")
                .team2("Steaua")
                .date(LocalDate.now())
                .goals1((byte) 3)
                .goals2((byte) -1)
                .build();

    }

}