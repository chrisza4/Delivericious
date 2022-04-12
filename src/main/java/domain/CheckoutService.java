package domain;

public class CheckoutService {
  private final PaymentService paymentService;

  public CheckoutService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public OrderRequest checkout(Basket basket) {
    paymentService.pay(basket.totalPrice());
    basket.markAsPaid();
    return new OrderRequest();
  }
}
