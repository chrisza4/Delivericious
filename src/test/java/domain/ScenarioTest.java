package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ScenarioTest {

  class MockPaymentService extends PaymentService {
    private Money paidPrice;

    @Override
    public void pay(Money price) {
      this.paidPrice = price;
    }

    public Money getPaidPrice() {
      return paidPrice;
    }
  }

  class MockEventPublisher extends EventPublisher {
    private List<String> publishedEvent = new ArrayList<>();

    @Override
    public void publish(String eventName, Object event) {
      this.publishedEvent.add(eventName);
    }

    public List<String> getPublishedEvent() {
      return this.publishedEvent;
    }
  }

  @Test
  void OurUseCases() throws BasketAmountExceedException {
    MenuItem tomatoSoup = new MenuItem("Tomato Soup", new Money(new BigDecimal("10.00"), Currency.SGD));
    MenuItem seaFoodSalad = new MenuItem("Sea food salad", new Money(new BigDecimal("12.00"), Currency.SGD));
    MenuItem chocolateIceCream = new MenuItem("Chocolate Ice Cream",
        new Money(new BigDecimal("4.00"), Currency.SGD));

    Basket basket = new Basket();
    BasketItem tomatoBasketItem = new BasketItem(tomatoSoup, 1);
    BasketItem seaFoodBasketItem = new BasketItem(seaFoodSalad, 1);
    BasketItem chocolateBasketItem = new BasketItem(chocolateIceCream, 3);
    basket.add(tomatoBasketItem);
    basket.add(seaFoodBasketItem);
    basket.add(chocolateBasketItem);

    Assertions.assertEquals(basket.basketItems(), List.of(tomatoBasketItem, seaFoodBasketItem, chocolateBasketItem),
        "Menu Items are not equal");

    basket.remove(chocolateBasketItem.getId(), 1);

    Assertions.assertEquals(2, basket.getBasketItemById(chocolateBasketItem.getId()).get().getQuantity());

    // 2 Icecream, 1 Tomato soup, 1 Seafood salad
    // 8$ + 10$ + 12$ = 30$

    var totalPrice = basket.totalPrice();
    Assertions.assertEquals(new Money(new BigDecimal("30.00"), Currency.SGD), totalPrice);
  }

  @Test
  void UseCase6ShouldValidateAmountOfBasket() {
    Basket basket = new Basket();

    MenuItem tomatoSoup = new MenuItem("Tomato Soup", new Money(new BigDecimal("100001.00"), Currency.SGD));
    Assertions.assertThrows(BasketAmountExceedException.class, () -> basket.add(new BasketItem(tomatoSoup, 1)));

  }

  @Test
  void UseCaseShouldBeAbleToSave() throws BasketAmountExceedException {
    BasketRepository repo = new BasketRepository();
    Basket basket = new Basket();
    MenuItem tomatoSoup = new MenuItem("Tomato Soup", new Money(new BigDecimal("10.00"), Currency.SGD));
    BasketItem tomatoBasketItem = new BasketItem(tomatoSoup, 1);
    basket.add(tomatoBasketItem);
    repo.Save(basket);

    Basket savedBasket = repo.getById(basket.getId());
    Assertions.assertEquals(savedBasket.basketItems(), List.of(tomatoBasketItem));
  }

  @Test
  void UseCaseShouldBeAbleToSuggestCoupon() throws BasketAmountExceedException {
    Basket basket = new Basket();
    MenuItem tomatoSoup = new MenuItem("tomato_soup", "Tomato Soup", new Money(new BigDecimal("10.00"), Currency.SGD));
    BasketItem tomatoBasketItem = new BasketItem(tomatoSoup, 3);
    basket.add(tomatoBasketItem);

    CouponSuggestionService service = new CouponSuggestionService();
    Coupon tomatoCoupon = new Coupon("TOMATO_001", 10.0);
    Coupon suggestedCoupon = service.SuggestCoupon(basket, List.of(tomatoCoupon));
    Assertions.assertEquals("TOMATO_001", suggestedCoupon.getCode());
  }

  @Test
  void UseCaseCheckout() throws BasketAmountExceedException {
    Basket basket = new Basket();
    MenuItem tomatoSoup = new MenuItem("tomato_soup", "Tomato Soup", new Money(new BigDecimal("10.00"), Currency.SGD));
    BasketItem tomatoBasketItem = new BasketItem(tomatoSoup, 3);
    basket.add(tomatoBasketItem);

    MockPaymentService mockPaymentService = new MockPaymentService();
    MockEventPublisher mockEventPublisher = new MockEventPublisher();
    CheckoutService checkoutService = new CheckoutService(mockPaymentService, mockEventPublisher);
    checkoutService.checkout(basket);

    Assertions.assertEquals(basket.totalPrice(), mockPaymentService.paidPrice);
    Assertions.assertTrue(basket.isPaid());

    Assertions.assertEquals(1, mockEventPublisher.getPublishedEvent().size());
    Assertions.assertEquals("BasketCheckout", mockEventPublisher.getPublishedEvent().get(0));

  }

}
