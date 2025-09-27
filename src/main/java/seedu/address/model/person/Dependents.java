package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's number of dependents in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDependents(int)}
 */
public class Dependents {

    public static final String MESSAGE_CONSTRAINTS = "Number of dependents must be a non-negative integer";

    public final int value;

    /**
     * Constructs a {@code Dependents}.
     *
     * @param numberOfDependents A valid number of dependents.
     */
    public Dependents(int numberOfDependents) {
        requireNonNull(numberOfDependents);
        checkArgument(isValidDependents(numberOfDependents), MESSAGE_CONSTRAINTS);
        value = numberOfDependents;
    }

    /**
     * Returns true if a given integer is a valid number of dependents.
     */
    public static boolean isValidDependents(int test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Dependents)) {
            return false;
        }

        Dependents otherDependents = (Dependents) other;
        return value == otherDependents.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
