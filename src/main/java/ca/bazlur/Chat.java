package ca.bazlur;

import java.time.LocalDateTime;

public record Chat(String message, LocalDateTime timestamp) {
}
