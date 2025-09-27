package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's marital status in the address book.
 * Guarantees: immutable; must be one of the predefined constants in {@link MaritalStatusEnum}.
 */
public class MaritalStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Marital status must be one of the predefined constants: "
                    + "Single, Married, Divorced, Widowed";

    public final MaritalStatusEnum value;

    /**
     * Constructs a {@code MaritalStatus}.
     *
     * @param maritalStatus A valid marital status.
     */
    public MaritalStatus(String maritalStatus) {
        requireNonNull(maritalStatus);
        checkArgument(MaritalStatusEnum.isValidMaritalStatus(maritalStatus), MESSAGE_CONSTRAINTS);
        value = MaritalStatusEnum.fromString(maritalStatus);
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MaritalStatus)) {
            return false;
        }

        MaritalStatus otherMaritalStatus = (MaritalStatus) other;
        return value.equals(otherMaritalStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
