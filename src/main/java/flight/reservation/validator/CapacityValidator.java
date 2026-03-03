package flight.reservation.validator;

import flight.reservation.flight.ScheduledFlight;

/**
 * Validator to check if flights have enough capacity for passengers.
 */
public class CapacityValidator extends FlightBookingValidator {

    @Override
    protected boolean doValidation(FlightBookingRequest request) {
        for (ScheduledFlight flight : request.getFlights()) {
            try {
                int availableCapacity = flight.getAvailableCapacity();
                if (availableCapacity < request.getPassengerNames().size()) {
                    request.setRejectionReason("Flight " + flight.getNumber() +
                            " does not have enough capacity.");
                    return false;
                }
            } catch (NoSuchFieldException e) {
                request.setRejectionReason("Cannot determine flight capacity.");
                return false;
            }
        }
        return true;
    }
}
