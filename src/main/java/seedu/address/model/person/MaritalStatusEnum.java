package seedu.address.model.person;

/**
 * Enum representing predefined marital statuses for a Person.
 * Each enum constant has a human-readable string representation.
 */
public enum MaritalStatusEnum {
    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed");

    private final String displayValue;


    MaritalStatusEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * Returns the human-readable string representation of the marital status.
     * @return The string representation of the marital status.
     */
    @Override
    public String toString() {
        return displayValue;
    }

    /**
     * Checks whether a given string is a valid marital status.
     * @param test
     * @return true if the string is a valid marital status, false otherwise.
     */
    public static boolean isValidMaritalStatus(String test) {
        for (MaritalStatusEnum maritalStatus : MaritalStatusEnum.values()) {
            if (maritalStatus.displayValue.equals(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a string to its corresponding MaritalStatusEnum constant value.
     * @param test The string representation of the marital status.
     * @return The corresponding MaritalStatusEnum.
     * @throws IllegalArgumentException if the string does not match any marital status.
     */
    public static MaritalStatusEnum fromString(String test) {
        for (MaritalStatusEnum maritalStatus : MaritalStatusEnum.values()) {
            if (maritalStatus.displayValue.equals(test)) {
                return maritalStatus;
            }
        }
        throw new IllegalArgumentException("Invalid marital status: " + test);
    }
}
