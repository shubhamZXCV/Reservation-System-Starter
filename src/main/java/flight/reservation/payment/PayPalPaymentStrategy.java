package flight.reservation.payment;

/**
 * Strategy for processing PayPal payments.
 * Encapsulates the PayPal payment algorithm.
 */
public class PayPalPaymentStrategy implements PaymentStrategy {

    private final String email;
    private final String password;

    public PayPalPaymentStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean validate() {
        return email != null &&
                password != null &&
                email.equals(Paypal.DATA_BASE.get(password));
    }

    @Override
    public boolean execute(double amount) throws IllegalStateException {
        if (!validate()) {
            throw new IllegalStateException("PayPal credentials are not valid.");
        }

        System.out.println("Paying " + amount + " using PayPal.");
        return true;
    }

    @Override
    public String getStrategyName() {
        return "PayPal Payment";
    }
}
