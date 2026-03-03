package flight.reservation.validator;

import java.util.Arrays;
import java.util.List;

/**
 * Validator to check if customer or passengers are on the no-fly list.
 */
public class NoFlyListValidator extends FlightBookingValidator {
    private static final List<String> NO_FLY_LIST = Arrays.asList("Peter", "Johannes");

    @Override
    protected boolean doValidation(FlightBookingRequest request) {
        // Check if customer is on no-fly list
        if (NO_FLY_LIST.contains(request.getCustomer().getName())) {
            request.setRejectionReason("Customer is on the no-fly list.");
            return false;
        }

        // Check if any passenger is on no-fly list
        for (String passengerName : request.getPassengerNames()) {
            if (NO_FLY_LIST.contains(passengerName)) {
                request.setRejectionReason("Passenger '" + passengerName + "' is on the no-fly list.");
                return false;
            }
        }

        return true;
    }
}
