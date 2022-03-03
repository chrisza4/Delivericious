import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketTest {

    @Test
    void shouldAddTomatoSoupToBasket() {
        MenuItem tomatoSoup = new MenuItem("Tomato Soup", new Money(new BigDecimal("10.00"), Currency.SGD));

        Basket basket = new Basket();
        basket.add(tomatoSoup);

        assertEquals(basket.menuItems(), Collections.singletonList(tomatoSoup), "Menu Items are not equal");
    }

    @Test
    void shouldAddSeaFoodSaladToBasket() {
        MenuItem seaFoodSalad = new MenuItem("Sea food salad", new Money(new BigDecimal("12.00"), Currency.SGD));

        Basket basket = new Basket();
        basket.add(seaFoodSalad);

        assertEquals(basket.menuItems(), Collections.singletonList(seaFoodSalad), "Menu Items are not equal");
    }

}
