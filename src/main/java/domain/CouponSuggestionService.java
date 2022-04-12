package domain;

import java.util.List;

public class CouponSuggestionService {
  public Coupon SuggestCoupon(Basket basket, List<Coupon> availableCoupons) {
    var items = basket.basketItems();
    var tomatoSoupItem = items.stream().filter(item -> item.getMenuItem().getId().equals("tomato_soup")).findAny();
    var tomatoCoupon = availableCoupons.stream().filter(coupon -> coupon.getCode().equals("TOMATO_001")).findAny();
    if (tomatoSoupItem.isEmpty()) {
      return null;
    }
    if (tomatoSoupItem.get().getQuantity() >= 3 && tomatoCoupon.isPresent()) {
      return tomatoCoupon.get();
    }
    return null;
  }
}
