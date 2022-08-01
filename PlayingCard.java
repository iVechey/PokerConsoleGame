/*
 * 
 * 
 * 
 */
public class PlayingCard extends Card{
	char suit;
	int value;
	
	public PlayingCard(char value, char suit) {
		setSuit(suit);
		setValue(value);
	}
	
	public PlayingCard(int value, char suit) {
		setSuit(suit);
		setValue(value);
	}
	
	public PlayingCard(int value, int suit) {
		setSuit(suit);
		setValue(value);
	}
	
	public PlayingCard(char value, int suit) {
		setSuit(suit);
		setValue(value);
	}
	
	
	public void printCard() {
		System.out.println(transformCard());
	}
	//transforms the card into human readable 
	//ie 1 = Ace 
	public String transformCard() {
		String toReturn = "" + this.suit;
		if (this.value >=2 && this.value < 11) {
			toReturn = this.value + toReturn;
		}
		else {
			switch (this.value) {
			
				case 11:
					toReturn = "J" + toReturn;
					break;
				case 12:
					toReturn = "Q" + toReturn;
					break;
				case 13:
					toReturn = "K" + toReturn;
					break;
				case 1:
					toReturn = "A" + toReturn;
					break;
			}
		}
		return toReturn;
	}
	
	public char getSuit() {
		return this.suit;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setSuit(char suit) {
		this.suit = suit;
	}
	
	public void setSuit(int suit) {
		switch(suit) {
			case 0:
				this.setSuit('h');
				break;
			case 1:
				this.setSuit('s');
				break;
			case 2:
				this.setSuit('c');
				break;
			case 3:
				this.setSuit('d');
				break;
		}
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	//To set if a character is passed through
	public void setValue(char value) {
		switch(value) {
			case 'J':
				this.value = 11;
				break;
			case 'j':
				this.value = 11;
				break;
			case 'Q':
				this.value = 12;
				break;
			case 'q':
				this.value = 12;
				break;
			case 'k':
				this.value = 13;
				break;
			case 'K':
				this.value = 13;
				break;
			case 'A':
				this.value = 1;
				break;
			case 'a':
				this.value = 1;
				break;
			
		}
	}
	
	public String toString() {
		
		return transformCard();
	}
	
}
