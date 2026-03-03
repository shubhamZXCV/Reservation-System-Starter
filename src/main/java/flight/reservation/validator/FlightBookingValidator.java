package flight.reservation.validator;

/**
 * Abstract handler for the Chain of Responsibility Pattern.
 * Forms a chain of validators that sequentially validate a booking request.
 */
public abstract class FlightBookingValidator {
    protected FlightBookingValidator nextValidator;

    /**
     * Validates the request and passes it to the next validator in the chain.
     * Template method pattern.
     *
     * @param request the booking request to validate
     * @return the request with validation results
     */
    public final FlightBookingRequest validate(FlightBookingRequest request) {
        if (request.isValid()) {
            // Only proceed if current request is still valid
            boolean isValid = doValidation(request);
            if (!isValid) {
                return request;
            }
        }

        // Continue to next handler if one exists
        if (nextValidator != null) {
            return nextValidator.validate(request);
        }

        return request;
    }

    /**
     * Template method to be implemented by subclasses.
     * Should perform specific validation and set rejection reason if validation fails.
     *
     * @param request the booking request
     * @return true if validation passes, false otherwise
     */
    protected abstract boolean doValidation(FlightBookingRequest request);

    /**
     * Sets the next validator in the chain.
     *
     * @param next the next validator
     */
    public void setNextValidator(FlightBookingValidator next) {
        this.nextValidator = next;
    }
}
