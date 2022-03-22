package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class ScenarioTest {

  @Test
  void OurUseCases() {
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

}
