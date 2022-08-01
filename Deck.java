import java.util.ArrayList;
import java.util.List;
public abstract class Deck implements IDeck{
	int size;
	List<PlayingCard> cards = new ArrayList<PlayingCard>();
	abstract public void shuffle();
	abstract public void initDeck();
	abstract public void printDeck();
	
}
