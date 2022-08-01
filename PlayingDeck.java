import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class PlayingDeck extends Deck{
	int size = 52;
	List<PlayingCard> cards = new ArrayList<PlayingCard>();
	
	
	public PlayingDeck() {
		shuffle();
	}
	@Override
	public PlayingCard draw() {
		size--;
		return cards.remove(0);
	}

	@Override
	public void insert(Card card) {
		// TODO Auto-generated method stub
		size++;
		cards.add((PlayingCard) card);
	}

	@Override
	public void shuffle() {
		initDeck();
		// TODO Auto-generated method stub
		Random rand = new Random();
		for(int i = 0; i < size; i++) {
			int to_flip = Math.abs(rand.nextInt() %52);
			PlayingCard cardOne = cards.get(to_flip);
			PlayingCard cardTwo = cards.get(i);
			cards.set(i, cardOne);
			cards.set(to_flip, cardTwo);
		}
		
		
	}
	
	public void shuffle(int noInit) {
		
		// TODO Auto-generated method stub
		Random rand = new Random();
		for(int i = 0; i < size; i++) {
			int to_flip = Math.abs(rand.nextInt() %52);
			PlayingCard cardOne = cards.get(to_flip);
			PlayingCard cardTwo = cards.get(i);
			cards.set(i, cardOne);
			cards.set(to_flip, cardTwo);
		}
		
		
	}
	
	

	@Override
	public void initDeck() {
		for(int i = 0; i < 52; i++) {
			PlayingCard card = new PlayingCard((i%13)+1, (i%4));
			cards.add(card);
		}
		
	}
	
	public void printDeck() {
		for(Card card : cards) {
			card.printCard();
		}
	}
	
	
}
