package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasketRepository {
  private Map<UUID, Basket> allBaskets;

  public BasketRepository() {
    this.allBaskets = new HashMap<>();
  }

  public void Save(Basket basket) {
    this.allBaskets.put(basket.getId(), basket);
  }

  public Basket getById(UUID id) {
    return this.allBaskets.get(id);
  }
}
