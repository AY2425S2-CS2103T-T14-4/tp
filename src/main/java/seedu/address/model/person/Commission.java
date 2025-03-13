package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
/**
 * Represents a Person's commission in the address book.
 */
public class Commission {

    public static final String MESSAGE_CONSTRAINTS = "Commission should only contain numbers, "
            + "not start with 0, "
            + "at most 9 digits long, "
            + "and it should not be blank";
    public static final String VALIDATION_REGEX = "^[1-9]\\d{0,8}$";
    public final String VALUE;

    /**
     * Constructs a {@code Commission}.
     *
     * @param commission
     */
    public Commission(String commission) {
        requireNonNull(commission);
        VALUE = commission;
    }

    /**
     * Returns true if a given string is a valid commission.
     */
    public static boolean isValidCommission(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a string type of the integer.
     */
    public static String getStringValue(int value) {
        return String.valueOf(value);
    }

    /**
     * Returns an integer type of the string.
     */
    public int getIntegerValue() {
        return Integer.parseInt(VALUE);
    }

    /**
     * Adds commission to the current one.
     * @return the added commission.
     */
    public Commission addValue(Commission commission) {
        int value = this.getIntegerValue() + commission.getIntegerValue();
        String commissionValue = getStringValue(value);
        return new Commission(commissionValue);
    }

    @Override
    public String toString() {
        return VALUE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Commission)) {
            return false;
        }

        Commission otherCommission = (Commission) other;
        return otherCommission.VALUE.equals(VALUE);
    }

    @Override
    public int hashCode() { return VALUE.hashCode(); }

}
