package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class CommissionLimitExceededException extends RuntimeException {
    public CommissionLimitExceededException() {
        super("Operation would result in exceeding commission limit: 1'000'000'000");
    }
}
