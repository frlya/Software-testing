package hw04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.DiscountedItem;
import shop.EShopController;
import shop.Item;
import shop.ShoppingCart;
import shop.StandardItem;
import storage.NoItemInStorage;

public class ProcessEShopControllerTest {
  private final EShopController shopController = new EShopController();
  private int[] itemCount;
  private Item[] storageItems;

  @BeforeEach
  public void init() {
    EShopController.startEShop();

    itemCount = new int[] {10, 10, 4, 5, 10, 2};

    storageItems = new Item[] {
        new StandardItem(1, "Mario", 5000, "GAMES", 5),
        new StandardItem(2, "MetalGearSolid", 6000, "GAMES", 10),
        new StandardItem(3, "Battlefield", 200, "GAMES", 5),
        new DiscountedItem(4, "Little Yoda toy", 500, "TOYS", 30, "4.3.2010", "1.10.2011"),
        new DiscountedItem(5, "Angry bird cup", 300, "STUFF", 20, "1.9.2013", "1.12.2013"),
        new DiscountedItem(6, "Nice cup", 800, "STUFF", 10, "1.8.2013", "1.12.2013")
    };

    for (int i = 0; i < storageItems.length; i++) {
      shopController.getStorage().insertItems(storageItems[i], itemCount[i]);
    }
  }

  @Test
  public void testPurchaseNonExistingItem() {
    ShoppingCart cart = new ShoppingCart();
    Item nonExistingItem = new StandardItem(100, "Wall", 1000, "Furniture", 10);
    cart.addItem(nonExistingItem);

    assertThrows(NoItemInStorage.class, () ->
        shopController.purchaseShoppingCart(cart, "Tony Hawk", "Skate park"));
  }

  @Test
  public void testPurchaseOutOfStockItem() {
    ShoppingCart cart = new ShoppingCart();
    shopController.getStorage().setItemCount(storageItems[4].getID(), 0);

    cart.addItem(storageItems[4]);
    assertThrows(NoItemInStorage.class, () ->
        shopController.purchaseShoppingCart(cart, "Solid Snake", "Secret Mission"));
  }

  @Test
  public void testPurchaseItemSuccess() {
    ShoppingCart cart = new ShoppingCart();
    cart.addItem(storageItems[3]);
    cart.addItem(storageItems[5]);

    try {
      shopController.purchaseShoppingCart(cart, "Lara Croft", "Jungle Tomb");
    } catch (Exception e) {
      fail("Unexpected NoItemInStorage exception.");
    }

    assertEquals(1, shopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[3]));
    assertEquals(1, shopController.getArchive().getHowManyTimesHasBeenItemSold(storageItems[5]));

    assertEquals(shopController.getStorage().getItemCount(storageItems[3].getID()), itemCount[3] - 1);
    assertEquals(shopController.getStorage().getItemCount(storageItems[5].getID()), itemCount[5] - 1);
  }

  @Test
  public void testRemoveItemFromEmptyCart() {
    ShoppingCart cart = new ShoppingCart();
    assertThrows(IllegalArgumentException.class, () -> cart.removeItem(storageItems[2].getID()));
  }

  @Test
  public void testRemoveItemNotInCart() {
    ShoppingCart cart = new ShoppingCart();
    cart.addItem(storageItems[0]);
    assertThrows(IllegalArgumentException.class, () -> cart.removeItem(storageItems[3].getID()));
  }

  @Test
  public void testRemoveItemFromCartSuccess() {
    ShoppingCart cart = new ShoppingCart();
    cart.addItem(storageItems[5]);
    cart.removeItem(storageItems[5].getID());
    assertFalse(cart.containsItem(storageItems[5]));
  }
}
