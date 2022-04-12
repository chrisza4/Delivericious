package domain;

public class Coupon {
  private String code;
  private Double discountPercentage;

  public Coupon(String code, Double discountPercentage) {
    this.code = code;
    this.discountPercentage = discountPercentage;
  }

  public String getCode() {
    return code;
  }

  public Double getDiscountPercentage() {
    return discountPercentage;
  }
}
