package flight.reservation.payment;

/**
 * PaymentProcessor interface - the target interface for the Adapter Pattern.
 * Unified interface for different payment methods.
 */
public interface PaymentProcessor {

    /**
     * Validates if the payment information is valid and can be processed.
     *
     * @return true if payment is valid, false otherwise
     */
    boolean isPaymentValid();

    /**
     * Processes the payment for the given amount.
     *
     * @param amount the amount to pay
     * @return true if payment is successful, false otherwise
     * @throws IllegalStateException if payment information is invalid
     */
    boolean processPayment(double amount) throws IllegalStateException;

    /**
     * Returns the name of the payment method.
     *
     * @return the payment method name
     */
    String getPaymentMethodName();
}
