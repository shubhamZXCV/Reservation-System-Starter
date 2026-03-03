package flight.reservation.payment;

/**
 * Strategy for processing Credit Card payments.
 * Encapsulates the credit card payment algorithm.
 */
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private final CreditCard creditCard;

    public CreditCardPaymentStrategy(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean validate() {
        return creditCard != null && creditCard.isValid();
    }

    @Override
    public boolean execute(double amount) throws IllegalStateException {
        if (!validate()) {
            throw new IllegalStateException("Credit card is not valid.");
        }

        double remainingAmount = creditCard.getAmount() - amount;
        if (remainingAmount < 0) {
            System.out.printf("Card limit reached - Balance: %f%n", remainingAmount);
            throw new IllegalStateException("Card limit reached");
        }

        System.out.println("Paying " + amount + " using Credit Card.");
        creditCard.setAmount(remainingAmount);
        return true;
    }

    @Override
    public String getStrategyName() {
        return "Credit Card Payment";
    }
}
