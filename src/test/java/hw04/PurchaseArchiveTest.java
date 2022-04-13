package hw04;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import archive.ItemPurchaseArchiveEntry;
import archive.PurchasesArchive;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.Item;
import shop.Order;
import shop.StandardItem;

@ExtendWith(MockitoExtension.class)
public class PurchaseArchiveTest {

  private final ArrayList<Item> items = new ArrayList<>(List.of(
      new StandardItem(1, "Mario", 3.99f, "Games", 0),
      new StandardItem(2, "Metal Gear", 10.99f, "Games", 0),
      new StandardItem(3, "Mario NEW", 1.99f, "Games", 30),
      new StandardItem(4, "Table", 100.99f, "Furniture", 0),
      new StandardItem(5, "Mario", 3.99f, "Games", 50),
      new StandardItem(6, "Battlefield 20", 113.99f, "Games", 100),
      new StandardItem(7, "No", 0.99f, "Games", 200)
  ));

  @Mock private Order orderMock;

  @Mock ItemPurchaseArchiveEntry firstPurchase;
  @Mock ItemPurchaseArchiveEntry secondPurchase;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  void nullTestItemPurchaseArchive() {
    var archive = new PurchasesArchive(null, null);
    assertDoesNotThrow(archive::printItemPurchaseStatistics);
  }

  @Test
  void nullTestOrderArchive() {
    var archive = new PurchasesArchive(null, null);
    assertDoesNotThrow(() -> archive.putOrderToPurchasesArchive(
        new Order(null, "", "", 0)));
  }

  @Test
  void nullTestNoArgsConstructorPrintPurchaseStatistics() {
    var archive = new PurchasesArchive();
    assertDoesNotThrow(archive::printItemPurchaseStatistics);
  }

  @Test
  void nullTestNoArgsConstructorPutOrderToPurchaseArchiveWithNullCart() {
    var archive = new PurchasesArchive();
    when(orderMock.getItems()).thenReturn(items);
    assertDoesNotThrow(() -> archive.putOrderToPurchasesArchive(orderMock));
  }

  @Test
  void printItemPurchaseStatistics() {
    when(firstPurchase.toString()).thenReturn("ITEM  Table   HAS BEEN SOLD 1 TIMES");
    when(secondPurchase.toString()).thenReturn("ITEM  Chair   HAS BEEN SOLD 2 TIMES");

    var itemArchive = new HashMap<>(Map.of(
        1,   firstPurchase,
        2, secondPurchase
    ));

    var orderArchive = new ArrayList<>(List.of(orderMock));
    var archive = new PurchasesArchive(itemArchive, orderArchive);
    archive.printItemPurchaseStatistics();

    assertEquals(
        "ITEM PURCHASE STATISTICS:\n"
            + "ITEM  Table   HAS BEEN SOLD 1 TIMES\n"
            + "ITEM  Chair   HAS BEEN SOLD 2 TIMES\n",
        outContent.toString()
    );
  }

  @Test
  void soldNegativeNumberOfTimes() {
      var item = items.get(0);
      var archive = new PurchasesArchive(
          new HashMap<>(Map.of(1, firstPurchase)), new ArrayList<>()
      );
      when(firstPurchase.getCountHowManyTimesHasBeenSold()).thenReturn(-123);
      assertTrue(archive.getHowManyTimesHasBeenItemSold(item) >= 0);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 2, 4, 19, 10, 234234})
  void soldPositiveNumberOfTimes(int num) {
    var item = items.get(0);
    var archive = new PurchasesArchive(
        new HashMap<>(Map.of(1, firstPurchase)), new ArrayList<>()
    );
    when(firstPurchase.getCountHowManyTimesHasBeenSold()).thenReturn(num);
    assertEquals(num, archive.getHowManyTimesHasBeenItemSold(item));
  }

  @Test
  void putOrderToArchiveEmptyTest() {
    var archive = new PurchasesArchive();
    when(orderMock.getItems()).thenReturn(items);
    archive.putOrderToPurchasesArchive(orderMock);
    var added = archive
        .getArchive().values().stream()
        .map(ItemPurchaseArchiveEntry::getRefItem)
        .toList();
    items.forEach(item -> {
     assertTrue(added.contains(item));
    });
    assertEquals(added.size(), items.size());
  }

  @Test
  void putOrderToArchiveCheckIds() {
    var archive = new PurchasesArchive();
    when(orderMock.getItems()).thenReturn(items);
    archive.putOrderToPurchasesArchive(orderMock);
    var addedIds = archive
        .getArchive().values().stream()
        .map(ItemPurchaseArchiveEntry::getRefItem)
        .mapToInt(Item::getID).toArray();
    var itemsIds = items.stream()
        .mapToInt(Item::getID).toArray();
    assertArrayEquals(addedIds, itemsIds);
  }

}
