package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Basket {
    private List<BasketItem> basketItems = new ArrayList<>();
    private static final Money AMOUNT_LIMIT = new Money(new BigDecimal(10000), Currency.SGD);
    private UUID id;
    private BasketStatus status;

    public Basket() {
        this(UUID.randomUUID());
    }

    public Basket(UUID id) {
        this.id = id;
    }

    public void add(BasketItem basketItem) throws BasketAmountExceedException {
        if (totalPrice().add(basketItem.price()).moreThan(AMOUNT_LIMIT)) {
            throw new BasketAmountExceedException();
        }
        basketItems.add(basketItem);
    }

    public UUID getId() {
        return id;
    }

    public void markAsPaid() {
        this.status = BasketStatus.Paid;
    }

    public Boolean isPaid() {
        return this.status == BasketStatus.Paid;
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
