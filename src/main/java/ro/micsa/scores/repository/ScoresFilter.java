package ro.micsa.scores.repository;

import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class ScoresFilter {
    private Optional<String> team = Optional.empty();
    private Optional<LocalDate> from = Optional.empty();
    private Optional<LocalDate> until = Optional.empty();
}
