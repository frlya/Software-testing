package hw04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import shop.StandardItem;

public class StandardItemTest {

  @ParameterizedTest
  @EmptySource
  void emptyName(String name) {
    var item = new StandardItem(0, name, 0, "Furniture", 0);
    Assertions.assertSame(item.getName(), name);
  }

  @ParameterizedTest
  @EmptySource
  void emptyCategory(String category) {
    var item = new StandardItem(0, "Table", 0, category, 0);
    Assertions.assertSame(item.getCategory(), category);
  }

  @ParameterizedTest
  @ValueSource(floats = {3.5f, 3.1f, 0.0f})
  void standardPrice(float price) {
    var item = new StandardItem(0, "SuperMario", price, "Video games", 0);
    Assertions.assertEquals(item.getPrice(), price);
  }

  @ParameterizedTest
  @ValueSource(floats = {-4.5f, -0.0f, -12312.0f})
  void negativePrice(float price) {
    var item = new StandardItem(0, "SuperMario", price, "Video games", 0);
    Assertions.assertTrue(item.getPrice() > 0.0f);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 234, Integer.MAX_VALUE, 0, 99999999})
  void standardId(int id) {
    var item = new StandardItem(id, "SuperMario", 9.99f, "Video games", 0);
    Assertions.assertEquals(item.getID(), id);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -234, -0, -23444, -99999999})
  void negativeId(int id) {
    var item = new StandardItem(id, "SuperMario", 9.99f, "Video games", 0);
    Assertions.assertTrue(item.getID() > 0);
  }

  @Test
  void testCopyEquality() {
    var item = new StandardItem(1, "SuperMario", 9.99f, "Video games", 0);
    var copy = item.copy();
    Assertions.assertEquals(item, copy);
  }

  @Test
  void testCopyNotSameReference() {
    var item = new StandardItem(1, "SuperMario", 9.99f, "Video games", 0);
    var copy = item.copy();
    Assertions.assertNotSame(item, copy);
  }

  @Test
  public void testEqualitySameReference() {
    var standardItem = new StandardItem(5, "Chair", 5.99f, "Furniture", 0);
    Assertions.assertEquals(standardItem, standardItem);
  }

  @Test
  public void testEqualitySameProperties() {
    var standardItem1 = new StandardItem(5, "Chair", 5.99f, "Furniture", 0);
    var standardItem2 = new StandardItem(5, "Chair", 5.99f, "Furniture", 0);
    Assertions.assertEquals(standardItem1, standardItem2);
  }


  @Test
  public void testEqualityNull() {
    var standardItem = new StandardItem(5, "Chair", 5.99f, "Furniture", 0);
    Assertions.assertNotEquals(null, standardItem);
  }





}
