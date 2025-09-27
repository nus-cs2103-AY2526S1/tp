package seedu.address.model.person;

/**
 * Enum representing predefined age ranges for a Person.
 * Each enum constant has a human-readable string representation.
 */
public enum AgeRangeEnum {
    UNDER_25("<25"),
    FROM_25_TO_34("25-34"),
    FROM_35_TO_44("35-44"),
    FROM_45_TO_54("45-54"),
    FROM_55_TO_64("55-64"),
    ABOVE_65("65+");

    private final String displayValue;


    AgeRangeEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * Returns the human-readable string representation of the age range.
     * @return The string representation of the age range.
     */
    @Override
    public String toString() {
        return displayValue;
    }

    /**
     * Checks whether a given string is a valid age range.
     * @param test
     * @return true if the string is a valid age range, false otherwise.
     */
    public static boolean isValidAgeRange(String test) {
        for (AgeRangeEnum ageRange : AgeRangeEnum.values()) {
            if (ageRange.displayValue.equals(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a string to its corresponding AgeRangeEnum constant value.
     * @param test The string representation of the age range.
     * @return The corresponding AgeRangeEnum.
     * @throws IllegalArgumentException if the string does not match any age range.
     */
    public static AgeRangeEnum fromString(String test) {
        for (AgeRangeEnum ageRange : AgeRangeEnum.values()) {
            if (ageRange.displayValue.equals(test)) {
                return ageRange;
            }
        }
        throw new IllegalArgumentException("Invalid age range: " + test);
    }
}
