package interview;

public class Main
{
  public static void main(String[] args) {

    multiplePlayers();

  }

  static void justOneThing() {
    Cache cache = new Cache(Player::new);
    Player payerName = cache.get("player name");

    boolean exists = cache.storage.containsKey("player name");

    System.out.println(exists);
    System.out.println(payerName.name);
  }

  static void multiplePlayers() {
    Cache cache = new Cache(Player::new);
    Player payerName1 = cache.get("player name 1");
    Player payerName2 = cache.get("player name 2");
    Player payerName3 = cache.get("player name 3");
    Player payerName4 = cache.get("player name 4");
    Player payerName5 = cache.get("player name 5");
    Player payerName6 = cache.get("player name 1");

    boolean exists1 = cache.storage.containsKey("player name 1");
    boolean exists2 = cache.storage.containsKey("player name 2");
    boolean exists3 = cache.storage.containsKey("player name 3");
    boolean exists4 = cache.storage.containsKey("player name 4");
    boolean exists5 = cache.storage.containsKey("player name 5");
    boolean exists6 = cache.storage.containsKey("player name 1");

    System.out.println(exists1);
    System.out.println(exists2);
    System.out.println(exists3);
    System.out.println(exists4);
    System.out.println(exists5);
    System.out.println(exists6);
  }


}