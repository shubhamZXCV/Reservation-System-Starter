package flight.reservation.observer;

import flight.reservation.order.FlightOrder;

/**
 * Email notification observer - sends email notifications for booking events.
 */
public class EmailNotificationObserver implements BookingObserver {

    @Override
    public void onBookingConfirmed(FlightOrder order) {
        String email = order.getCustomer().getEmail();
        System.out.println("[EMAIL] Booking confirmation sent to: " + email);
    }

    @Override
    public void onPaymentProcessed(FlightOrder order, double amount) {
        String email = order.getCustomer().getEmail();
        System.out.println("[EMAIL] Payment receipt sent to: " + email +
                " for amount: $" + amount);
    }

    @Override
    public void onBookingCancelled(FlightOrder order) {
        String email = order.getCustomer().getEmail();
        System.out.println("[EMAIL] Cancellation notice sent to: " + email);
    }
}
