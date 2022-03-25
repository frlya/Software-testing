package hwCv5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shop.Order;
import shop.ShoppingCart;

public class OrderTest {

  @Test
  void emptyItemsInOrder() {
    var cart = new ShoppingCart();
    var order = new Order(
        cart, "John", "Liberty lane, 69", 100
    );
    Assertions.assertSame(cart.getCartItems(), order.getItems());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "Xi","Li", "E", "Andrew",
      "Li Quan U" })
  void shortCustomerNameInOrder(String name) {
    var cart = new ShoppingCart();
    var order = new Order(
        cart, name, "Liberty lane, 69", 100
    );
    Assertions.assertSame(name, order.getCustomerName());
  }


  @ParameterizedTest
  @ValueSource(strings = {"Степан Андреевич", "Потап", "Йцукен выфарфылвоа", "Ἀλέξανδρος"})
  void CyrillicNamesInOrder(String name) {
    var cart = new ShoppingCart();
    var order = new Order(
        cart, name, "Liberty lane, 69", 100
    );
    Assertions.assertSame(name, order.getCustomerName());
  }


  @ParameterizedTest
  @ValueSource(strings = {"陈黄", "남궁", "日本"})
  void AsianNamesInOrder(String name) {
    var cart = new ShoppingCart();
    var order = new Order(
        cart, name, "Liberty lane, 69", 100
    );
    Assertions.assertSame(name, order.getCustomerName());
  }


  @ParameterizedTest
  @ValueSource(strings = {
      "HUBERT BLAINE WOLFE-SCHLEGEL-ADHAUSEN-BERGER-DORFF SR",
      "Константин Воскобойников",
      "František Červenokostelecký"
  })
  void longNamesInOrder(String name) {
    var cart = new ShoppingCart();
    var order = new Order(
        cart, name, "Liberty lane, 69", 100
    );
    Assertions.assertSame(name, order.getCustomerName());
  }

  @ParameterizedTest
  @EmptySource
  void noNameInOrder(String name) {
    var cart = new ShoppingCart();
    var order = new Order(
        cart, name, "Liberty lane, 69", 100
    );
    Assertions.assertSame(name, order.getCustomerName());
  }

  @Test
  void nullShoppingCartInOrder() {
    Assertions.assertDoesNotThrow(() -> {
      new Order(null, "", "", 0);
    });
  }

}
