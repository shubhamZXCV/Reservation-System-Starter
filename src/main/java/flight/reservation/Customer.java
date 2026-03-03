package flight.reservation;

import flight.reservation.flight.ScheduledFlight;
import flight.reservation.order.FlightOrder;
import flight.reservation.order.Order;
import flight.reservation.validator.CapacityValidator;
import flight.reservation.validator.FlightBookingRequest;
import flight.reservation.validator.FlightBookingValidator;
import flight.reservation.validator.NoFlyListValidator;
import flight.reservation.validator.PriceValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {

    private String email;
    private String name;
    private List<Order> orders;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.orders = new ArrayList<>();
    }

    public FlightOrder createOrder(List<String> passengerNames, List<ScheduledFlight> flights, double price) {
        if (!isOrderValid(passengerNames, flights)) {
            throw new IllegalStateException("Order is not valid");
        }
        FlightOrder order = new FlightOrder(flights);
        order.setCustomer(this);
        order.setPrice(price);
        List<Passenger> passengers = passengerNames
                .stream()
                .map(Passenger::new)
                .collect(Collectors.toList());
        order.setPassengers(passengers);
        order.getScheduledFlights().forEach(scheduledFlight -> scheduledFlight.addPassengers(passengers));
        orders.add(order);
        return order;
    }

    private boolean isOrderValid(List<String> passengerNames, List<ScheduledFlight> flights) {
        return isOrderValid(passengerNames, flights, 0.0);
    }

    /**
     * Validates the order using the Chain of Responsibility pattern.
     * Builds a validator chain and checks all validations sequentially.
     *
     * @param passengerNames the list of passenger names
     * @param flights the list of flights
     * @param price the price of the order
     * @return true if order is valid, false otherwise
     */
    private boolean isOrderValid(List<String> passengerNames, List<ScheduledFlight> flights, double price) {
        // Build the validator chain
        FlightBookingValidator noFlyListValidator = new NoFlyListValidator();
        FlightBookingValidator capacityValidator = new CapacityValidator();
        FlightBookingValidator priceValidator = new PriceValidator();

        // Chain the validators
        noFlyListValidator.setNextValidator(capacityValidator);
        capacityValidator.setNextValidator(priceValidator);

        // Create the request and validate
        FlightBookingRequest request = new FlightBookingRequest(this, passengerNames, flights, price);
        FlightBookingRequest result = noFlyListValidator.validate(request);

        return result.isValid();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
