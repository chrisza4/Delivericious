package domain;

public class CheckoutService {
  private final PaymentService paymentService;
  private final EventPublisher eventPublisher;

  public CheckoutService(PaymentService paymentService, EventPublisher eventPublisher) {
    this.paymentService = paymentService;
    this.eventPublisher = eventPublisher;
  }

  public void checkout(Basket basket) {
    paymentService.pay(basket.totalPrice());
    basket.markAsPaid();
    eventPublisher.publish("BasketCheckout", null);
  }
}
