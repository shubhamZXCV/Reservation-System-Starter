package flight.reservation.observer;

import flight.reservation.order.FlightOrder;

/**
 * Logging observer - logs booking events for auditing and monitoring.
 */
public class LoggingObserver implements BookingObserver {

    @Override
    public void onBookingConfirmed(FlightOrder order) {
        System.out.println("[LOG] Booking confirmed - Order ID: " + order.getId() +
                " for customer: " + order.getCustomer().getName());
    }

    @Override
    public void onPaymentProcessed(FlightOrder order, double amount) {
        System.out.println("[LOG] Payment processed - Order ID: " + order.getId() +
                " Amount: $" + amount);
    }

    @Override
    public void onBookingCancelled(FlightOrder order) {
        System.out.println("[LOG] Booking cancelled - Order ID: " + order.getId());
    }
}
