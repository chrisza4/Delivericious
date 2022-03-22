package domain;

import java.math.BigDecimal;
import java.util.UUID;

public class BasketItem {
    private final UUID id;
    private final MenuItem menuItem;
    private int quantity;

    public BasketItem(MenuItem menuItem, int quantity) {
        this(UUID.randomUUID(), menuItem, quantity);
    }

    public BasketItem(UUID id, MenuItem menuItem, int quantity) {
        this.id = id;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int by) {
        this.quantity -= by;
    }

    public Money price() {
        return menuItem.getPrice().multiplyBy(new BigDecimal(this.quantity));
    }
}
