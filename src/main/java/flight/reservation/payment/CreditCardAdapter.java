package flight.reservation.payment;

/**
 * Adapter for CreditCard to implement the PaymentProcessor interface.
 * Adapts the CreditCard class to the PaymentProcessor interface.
 */
public class CreditCardAdapter implements PaymentProcessor {

    private final CreditCard creditCard;

    public CreditCardAdapter(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean isPaymentValid() {
        return creditCard != null && creditCard.isValid();
    }

    @Override
    public boolean processPayment(double amount) throws IllegalStateException {
        if (!isPaymentValid()) {
            throw new IllegalStateException("Payment information is not set or not valid.");
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
    public String getPaymentMethodName() {
        return "Credit Card";
    }
}
