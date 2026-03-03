package flight.reservation.validator;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;

import java.util.List;

/**
 * Request object for booking validation.
 * Contains all information needed to validate a flight booking.
 */
public class FlightBookingRequest {
    private final Customer customer;
    private final List<String> passengerNames;
    private final List<ScheduledFlight> flights;
    private final double price;
    private String rejectionReason;

    public FlightBookingRequest(Customer customer,
                                List<String> passengerNames,
                                List<ScheduledFlight> flights,
                                double price) {
        this.customer = customer;
        this.passengerNames = passengerNames;
        this.flights = flights;
        this.price = price;
        this.rejectionReason = null;
    }

    // Getters
    public Customer getCustomer() {
        return customer;
    }

    public List<String> getPassengerNames() {
        return passengerNames;
    }

    public List<ScheduledFlight> getFlights() {
        return flights;
    }

    public double getPrice() {
        return price;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String reason) {
        this.rejectionReason = reason;
    }

    public boolean isValid() {
        return rejectionReason == null;
    }
}
