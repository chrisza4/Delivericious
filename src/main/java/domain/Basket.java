package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Basket {
    private List<BasketItem> basketItems = new ArrayList<>();

    public void add(BasketItem basketItem) {
        basketItems.add(basketItem);
    }

    public void remove(UUID basketItemId, int quantity) {
        var optionalMenuItem = this.basketItems.stream().filter(basketItem -> basketItem.getId().equals(basketItemId))
                .findFirst();
        if (optionalMenuItem.isEmpty()) {
            return;
        }
        var menuItem = optionalMenuItem.get();
        menuItem.reduceQuantity(quantity);

        if (menuItem.getQuantity() == 0) {
            this.basketItems.remove(menuItem);
        }
    }

    public List<BasketItem> basketItems() {
        return this.basketItems;
    }

    public Optional<BasketItem> getBasketItemById(UUID id) {
        return this.basketItems.stream().filter(basketItem -> basketItem.getId().equals(id)).findFirst();
    }

    public Money totalPrice() {
        var startMoney = new Money(new BigDecimal(0), Currency.SGD);
        return basketItems.stream().map(BasketItem::price).reduce(startMoney,
                (current, price) -> current.add(price));
    }
}
