package flight.reservation.observer;

import flight.reservation.order.FlightOrder;

/**
 * Observer interface for booking events.
 * Implementations can react to important booking events.
 */
public interface BookingObserver {

    /**
     * Called when a booking is confirmed.
     *
     * @param order the flight order that was confirmed
     */
    void onBookingConfirmed(FlightOrder order);

    /**
     * Called when a payment is processed successfully.
     *
     * @param order the flight order
     * @param amount the amount that was paid
     */
    void onPaymentProcessed(FlightOrder order, double amount);

    /**
     * Called when a booking is cancelled.
     *
     * @param order the flight order that was cancelled
     */
    void onBookingCancelled(FlightOrder order);
}
