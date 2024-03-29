package interview

import java.util.function.Function

import spock.lang.Specification
import spock.lang.Subject

class CacheSpec
    extends Specification
{
  Function<String, Player> playerFetcher = Mock()

  @Subject
  Cache cache = new Cache(playerFetcher)

  def 'adds and returns a player'() {
    when:
      def actual = cache.get('player name')

    then:
      actual.name == 'player name'

    and: 'player 1 is fetched only once'
      1 * playerFetcher.apply('player name') >> new Player('player name')
  }

  def 'playerFetcher is only invoked once per player'() {
    when:
      def actual1_1 = cache.get('player 1')
      def actual1_2 = cache.get('player 1')
      def actual2_1 = cache.get('player 2')
      def actual2_2 = cache.get('player 2')

    then:
      1 * playerFetcher.apply('player 1') >> new Player('player 1')
      1 * playerFetcher.apply('player 2') >> new Player('player 2')

    and: 'check object identity'
      actual1_1.is(actual1_2)
      actual2_1.is(actual2_2)

    and:
      actual1_2.name == 'player 1'
      actual2_2.name == 'player 2'
  }

  def 'old players are evicted'() {
    when:
      cache.get('player 1')
      cache.get('player 2')
      cache.get('player 3')
      cache.get('player 4')

    then:
      cache.storage.keySet() == ['player 3', 'player 4'] as Set
  }

  def 'when a player is accessed then the player is treated like a newly inserted player for eviction order'() {
    when:
      cache.get('player 1')
      cache.get('player 2')
      cache.get('player 1') // refresh player 1
      cache.get('player 3')
      cache.get('player 1') // refresh player 1
      cache.get('player 4')

    then:
      cache.storage.keySet() == ['player 1', 'player 4'] as Set

    and: 'player 1 is fetched only once'
      1 * playerFetcher.apply('player 1') >> new Player('player 1')
  }
}
