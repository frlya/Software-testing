package hw04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import shop.StandardItem;

public class ItemStockTest {

  public StandardItem itemForTest =
      new StandardItem(1, "Mario", 9.234f, "games", 10);

  @ParameterizedTest
  @ValueSource(ints = {0, 3, 345345, Integer.MAX_VALUE})
  void testIncreaseCountInItemStock(int number) {
    var item = new ItemStock(itemForTest);
    item.IncreaseItemCount(number);
    Assertions.assertEquals(number, item.getCount());
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 3, 345345, Integer.MAX_VALUE})
  void decreaseCountInItemStockWithZeroCount(int number) {
    var item = new ItemStock(itemForTest);
    item.decreaseItemCount(number);
    Assertions.assertTrue(item.getCount() >= 0);
  }

  @Test
  void noItemProvidedButCountSet() {
    var item = new ItemStock(null);
    item.IncreaseItemCount(100);
    item.decreaseItemCount(1);
    Assertions.assertTrue(
        item.getItem() == null &&
            item.getCount() == 0
    );
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 3, 345345, Integer.MAX_VALUE})
  void decreaseAndIncreaseNumberMustBeZero(int number){
    var item = new ItemStock(itemForTest);
    item.decreaseItemCount(number);
    item.IncreaseItemCount(number);
    Assertions.assertEquals(0, item.getCount());
  }
}
