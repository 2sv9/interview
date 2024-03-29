package interview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Function;

public class Cache
{
  private final Function<String, Player> playerFetcher;

  final HashMap<String, Player> storage = new HashMap<>();

  final LinkedList<String> order = new LinkedList<>();

  private static final int MAX_PLAYERS = 2;

  public Cache(Function<String, Player> playerFetcher) {
    this.playerFetcher = playerFetcher;
  }

  public Player get(final String key) {

    if (storage.containsKey(key)) {
      return getPlayer(key);
    }
    else {
      Player player = playerFetcher.apply(key);
      storePlayer(key, player);
      return player;
    }
  }

  private Player getPlayer(final String key) {
    order.remove(key);
    order.addFirst(key);
    return storage.get(key);
  }

  private void storePlayer(final String key, final Player player) {

    if (storage.size() >= MAX_PLAYERS) {
      String keyToEvict = order.getLast();
      order.remove(keyToEvict);
      storage.remove(keyToEvict);
    }

    storage.put(key, player);
    order.addFirst(key);
  }
}
