package flight.reservation.payment;

/**
 * Adapter for PayPal to implement the PaymentProcessor interface.
 * Adapts the PayPal static database to the PaymentProcessor interface.
 */
public class PayPalAdapter implements PaymentProcessor {

    private final String email;
    private final String password;

    public PayPalAdapter(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean isPaymentValid() {
        return email != null &&
                password != null &&
                email.equals(Paypal.DATA_BASE.get(password));
    }

    @Override
    public boolean processPayment(double amount) throws IllegalStateException {
        if (!isPaymentValid()) {
            throw new IllegalStateException("Payment information is not set or not valid.");
        }

        System.out.println("Paying " + amount + " using PayPal.");
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
}
