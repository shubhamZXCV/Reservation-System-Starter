package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.observer.BookingObserver;
import flight.reservation.payment.CreditCard;
import flight.reservation.payment.CreditCardAdapter;
import flight.reservation.payment.CreditCardPaymentStrategy;
import flight.reservation.payment.Paypal;
import flight.reservation.payment.PaymentProcessor;
import flight.reservation.payment.PayPalAdapter;
import flight.reservation.payment.PaymentStrategy;
import flight.reservation.payment.PayPalPaymentStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FlightOrder extends Order {
    private final List<ScheduledFlight> flights;
    private final List<BookingObserver> observers = new ArrayList<>();
    static List<String> noFlyList = Arrays.asList("Peter", "Johannes");

    public FlightOrder(List<ScheduledFlight> flights) {
        this.flights = flights;
    }

    public static List<String> getNoFlyList() {
        return noFlyList;
    }

    public List<ScheduledFlight> getScheduledFlights() {
        return flights;
    }

    /**
     * Add an observer to this order.
     *
     * @param observer the observer to add
     */
    public void addObserver(BookingObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Remove an observer from this order.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(BookingObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers that booking has been confirmed.
     */
    private void notifyBookingConfirmed() {
        for (BookingObserver observer : observers) {
            observer.onBookingConfirmed(this);
        }
    }

    /**
     * Notify all observers that payment has been processed.
     *
     * @param amount the amount paid
     */
    private void notifyPaymentProcessed(double amount) {
        for (BookingObserver observer : observers) {
            observer.onPaymentProcessed(this, amount);
        }
    }

    /**
     * Notify all observers that booking has been cancelled.
     */
    private void notifyBookingCancelled() {
        for (BookingObserver observer : observers) {
            observer.onBookingCancelled(this);
        }
    }

    private boolean isOrderValid(Customer customer, List<String> passengerNames, List<ScheduledFlight> flights) {
        boolean valid = true;
        valid = valid && !noFlyList.contains(customer.getName());
        valid = valid && passengerNames.stream().noneMatch(passenger -> noFlyList.contains(passenger));
        valid = valid && flights.stream().allMatch(scheduledFlight -> {
            try {
                return scheduledFlight.getAvailableCapacity() >= passengerNames.size();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return false;
            }
        });
        return valid;
    }

    public boolean processOrderWithCreditCardDetail(String number, Date expirationDate, String cvv) throws IllegalStateException {
        CreditCard creditCard = new CreditCard(number, expirationDate, cvv);
        return processOrderWithCreditCard(creditCard);
    }

    public boolean processOrderWithCreditCard(CreditCard creditCard) throws IllegalStateException {
        if (isClosed()) {
            // Payment is already proceeded
            return true;
        }
        // validate payment information
        if (!cardIsPresentAndValid(creditCard)) {
            throw new IllegalStateException("Payment information is not set or not valid.");
        }
        boolean isPaid = payWithCreditCard(creditCard, this.getPrice());
        if (isPaid) {
            this.setClosed();
            notifyBookingConfirmed();
            notifyPaymentProcessed(this.getPrice());
        }
        return isPaid;
    }

    private boolean cardIsPresentAndValid(CreditCard card) {
        return card != null && card.isValid();
    }

    /**
     * Process payment using the adapter-based PaymentProcessor interface.
     * This is the new way to process payments using adapters.
     *
     * @param processor the payment processor (adapter)
     * @return true if payment is successful
     * @throws IllegalStateException if payment fails
     */
    public boolean processPayment(PaymentProcessor processor) throws IllegalStateException {
        if (isClosed()) {
            return true;
        }
        if (processor == null || !processor.isPaymentValid()) {
            throw new IllegalStateException("Payment information is not set or not valid.");
        }
        boolean isPaid = processor.processPayment(this.getPrice());
        if (isPaid) {
            this.setClosed();
        }
        return isPaid;
    }

    /**
     * Process payment using the strategy-based PaymentStrategy interface.
     * This uses the Strategy Pattern to handle different payment algorithms.
     *
     * @param strategy the payment strategy
     * @return true if payment is successful
     * @throws IllegalStateException if strategy validation fails or payment fails
     */
    public boolean processPaymentWithStrategy(PaymentStrategy strategy) throws IllegalStateException {
        if (isClosed()) {
            return true;
        }
        if (strategy == null || !strategy.validate()) {
            throw new IllegalStateException("Payment strategy is invalid.");
        }
        boolean isPaid = strategy.execute(this.getPrice());
        if (isPaid) {
            this.setClosed();
            notifyBookingConfirmed();
            notifyPaymentProcessed(this.getPrice());
        }
        return isPaid;
    }

    public boolean processOrderWithPayPal(String email, String password) throws IllegalStateException {
        if (isClosed()) {
            // Payment is already proceeded
            return true;
        }
        // validate payment information
        if (email == null || password == null || !email.equals(Paypal.DATA_BASE.get(password))) {
            throw new IllegalStateException("Payment information is not set or not valid.");
        }
        boolean isPaid = payWithPayPal(email, password, this.getPrice());
        if (isPaid) {
            this.setClosed();
            notifyBookingConfirmed();
            notifyPaymentProcessed(this.getPrice());
        }
        return isPaid;
    }

    public boolean payWithCreditCard(CreditCard card, double amount) throws IllegalStateException {
        if (cardIsPresentAndValid(card)) {
            System.out.println("Paying " + getPrice() + " using Credit Card.");
            double remainingAmount = card.getAmount() - getPrice();
            if (remainingAmount < 0) {
                System.out.printf("Card limit reached - Balance: %f%n", remainingAmount);
                throw new IllegalStateException("Card limit reached");
            }
            card.setAmount(remainingAmount);
            return true;
        } else {
            return false;
        }
    }

    public boolean payWithPayPal(String email, String password, double amount) throws IllegalStateException {
        if (email.equals(Paypal.DATA_BASE.get(password))) {
            System.out.println("Paying " + getPrice() + " using PayPal.");
            return true;
        } else {
            return false;
        }
    }
}
