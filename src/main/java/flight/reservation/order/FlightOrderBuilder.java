package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.Passenger;
import flight.reservation.flight.ScheduledFlight;

import java.util.List;
import java.util.UUID;

/**
 * Builder for FlightOrder - implements the Builder Pattern.
 * Provides a fluent interface for creating FlightOrder instances.
 */
public class FlightOrderBuilder {
    private final Customer customer;
    private final List<ScheduledFlight> flights;
    private List<Passenger> passengers;
    private double price;

    /**
     * Create a builder with required fields.
     *
     * @param customer the customer making the order
     * @param flights the scheduled flights for this order
     */
    public FlightOrderBuilder(Customer customer, List<ScheduledFlight> flights) {
        if (customer == null || flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Customer and flights are required.");
        }
        this.customer = customer;
        this.flights = flights;
        this.price = 0.0;
    }

    /**
     * Set the passengers for this order.
     *
     * @param passengers the list of passengers
     * @return this builder for method chaining
     */
    public FlightOrderBuilder withPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
        return this;
    }

    /**
     * Set the price for this order.
     *
     * @param price the price
     * @return this builder for method chaining
     */
    public FlightOrderBuilder withPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
        return this;
    }

    /**
     * Build the FlightOrder instance.
     * This validates all required fields before creating the order.
     *
     * @return the built FlightOrder
     * @throws IllegalStateException if required fields are missing
     */
    public FlightOrder build() throws IllegalStateException {
        if (passengers == null || passengers.isEmpty()) {
            throw new IllegalStateException("Passengers are required for the order.");
        }

        FlightOrder order = new FlightOrder(flights);
        order.setCustomer(customer);
        order.setPrice(price);
        order.setPassengers(passengers);

        return order;
    }
}
