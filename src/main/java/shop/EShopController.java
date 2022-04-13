package shop;

import archive.PurchasesArchive;
import java.util.ArrayList;
import storage.NoItemInStorage;
import storage.Storage;


public class EShopController {

  private static Storage storage;
  private static PurchasesArchive archive;
  private static ArrayList<ShoppingCart> carts;
  private static ArrayList<Order> orders;

  public EShopController() {
  }


  public static void purchaseShoppingCart(ShoppingCart cart, String customerName,
      String customerAddress) throws NoItemInStorage {
    if (cart.getCartItems().isEmpty()) {
      System.out.println("Error: shopping cart is empty");
    }
    Order order = new Order(cart, customerName, customerAddress);
    storage.processOrder(order);
    archive.putOrderToPurchasesArchive(order);
  }

  public static ShoppingCart newCart() {
    ShoppingCart newCart = new ShoppingCart();
    carts.add(newCart);
    return newCart;
  }

  public static void startEShop() {
    if (storage == null) {
      storage = new Storage();
      archive = new PurchasesArchive();
      carts = new ArrayList();
      orders = new ArrayList();
    }
  }

  public Storage getStorage() {
    return storage;
  }

  public PurchasesArchive getArchive() {
    return archive;
  }
}

