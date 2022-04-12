package domain;

import java.util.Objects;

public class MenuItem {
    private String id;
    private String name;
    private Money price;

    public MenuItem(String id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public MenuItem(String name, Money price) {
        this("", name, price);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        MenuItem otherMenuItem = (MenuItem) other;
        return Objects.equals(name, otherMenuItem.name) &&
                Objects.equals(price, otherMenuItem.price);
    }

    public Money getPrice() {
        return this.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "domain.MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
