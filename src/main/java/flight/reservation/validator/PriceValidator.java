package flight.reservation.validator;

/**
 * Validator to check if the price is valid (positive and reasonable).
 */
public class PriceValidator extends FlightBookingValidator {
    private static final double MAXIMUM_PRICE = 1000000;

    @Override
    protected boolean doValidation(FlightBookingRequest request) {
        double price = request.getPrice();

        // Allow price of 0 or positive prices up to maximum
        if (price < 0) {
            request.setRejectionReason("Price cannot be negative.");
            return false;
        }

        if (price > MAXIMUM_PRICE) {
            request.setRejectionReason("Price exceeds maximum allowed value.");
            return false;
        }

        return true;
    }
}
