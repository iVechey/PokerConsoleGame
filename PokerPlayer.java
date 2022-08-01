
public class PokerPlayer {
	double chips;
	PlayingCard[] hand;
	int prePosition;
	int ID;
	
	public PokerPlayer(int chips, int ID) {
		this.chips = chips;
		this.hand = new PlayingCard[2];
		this.ID = ID;
	}
	
	
	public void setHand(PlayingCard card1, PlayingCard card2) {
		hand[0] = card1;
		hand[1] = card2;
	}
	
	public PlayingCard[] getHand() {
		return hand;
	}
	
	public double getChips() {
		return chips;
	}
	
	public void setChips(double amount) {
		chips = amount;
		
	}
	
	public void addChips(double chips2) {
		setChips(chips + chips2);
	}
	
	public String toString() {
		return hand[0] + " " + hand[1];
	}
	
	public void setPosition(int position) {
		prePosition = position;
	}
	
	public int getPosition() {
		return prePosition;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	
}
