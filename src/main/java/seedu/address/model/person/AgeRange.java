package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age range in the address book.
 * Guarantees: immutable; must be one of the predefined ranges in {@link AgeRangeEnum}.
 */
public class AgeRange {

    public static final String MESSAGE_CONSTRAINTS =
            "Age range must be one of the predefined ranges: "
            + "<25, 25-34, 35-44, 45-54, 55-64, >65";

    public final AgeRangeEnum value;

    /**
     * Constructs an {@code AgeRange}.
     *
     * @param ageRange A valid age range.
     */
    public AgeRange(String ageRange) {
        requireNonNull(ageRange);
        checkArgument(AgeRangeEnum.isValidAgeRange(ageRange), MESSAGE_CONSTRAINTS);
        value = AgeRangeEnum.fromString(ageRange);
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
        if (!(other instanceof AgeRange)) {
            return false;
        }

        AgeRange otherAgeRange = (AgeRange) other;
        return value.equals(otherAgeRange.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
