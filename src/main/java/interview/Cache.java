package interview;

import java.util.LinkedHashMap;
import java.util.function.Function;

public class Cache
{
  private final Function<String, Player> playerFetcher;

  private final LinkedHashMap<String, Player> storage = new LinkedHashMap<>(256, 0.75f,  /* accessOrder */ true);

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
    return storage.get(key);
  }

  private void storePlayer(final String key, final Player player) {

    if (storage.size() >= MAX_PLAYERS) {
      String firstPlayer = storage.keySet().iterator().next();
      storage.remove(firstPlayer);
    }

    storage.put(key, player);
  }
}
