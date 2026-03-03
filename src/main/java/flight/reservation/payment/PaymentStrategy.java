package flight.reservation.payment;

/**
 * PaymentStrategy interface - defines the contract for payment strategies.
 * Different payment methods can implement this interface.
 */
public interface PaymentStrategy {

    /**
     * Validates if the payment can be processed.
     *
     * @return true if payment information is valid, false otherwise
     */
    boolean validate();

    /**
     * Executes the payment for the given amount.
     *
     * @param amount the amount to pay
     * @return true if payment is successful
     * @throws IllegalStateException if validation fails or payment fails
     */
    boolean execute(double amount) throws IllegalStateException;

    /**
     * Returns the name of the payment strategy.
     *
     * @return the strategy name
     */
    String getStrategyName();
}
