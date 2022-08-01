import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class PokerGame {
	
	public static void main(String[] args) {
		
		PlayingDeck deck = new PlayingDeck();
		
		//we will later want to make this an if for a tournament set up
		//if args[2] != 1 {
		final int BB = 2;
		final int SB = 1;
		double ante = 0;
		double pot = 0;
		double bet;
		PlayingCard[] communityCards = new PlayingCard[5];
		Queue<String> preflopAction = new ArrayDeque<String>();
		Queue<String> flopAction = new ArrayDeque<String>();
		Queue<String> turnAction = new ArrayDeque<String>();
		Queue<String> riverAction = new ArrayDeque<String>();
		
		
		//We need to convert the preflop positions to a string
		
		
		
		//}
	
		List<PokerPlayer> players =  new ArrayList<PokerPlayer>();
		for(int p = 0; p < 9; p++) {
			players.add(new PokerPlayer(10000, p+1));			
		}
		
		int UTG = getStartingPlayer(players, deck);
		Queue<PokerPlayer> preflopQueue = new ArrayDeque<PokerPlayer>();
		int position = 0;
		for(PokerPlayer player : players) {
			preflopQueue.add(player);
			player.prePosition = position;
			position++;
			
		}
		//Queue<PokerPlayer> postflopQueue = getPostflop(preflopQueue);
		System.gc(); //we want to get rid of the list
		
		while(true) {
			
			//shuffle and init deck
			deck.shuffle();
			bet = BB;
			
			
			//give every player a hand
			for(PokerPlayer player: preflopQueue) {
				player.setHand(deck.draw(), deck.draw());
				if(player.prePosition <= 7) {
					continue;
				}
				if(player.prePosition == 8) {
					pot += SB;
				} else if(player.prePosition == 9) {
					pot += BB;
				}
			}
			Queue<PokerPlayer> postflopQueue = new ArrayDeque<>();;
			Scanner scanner = null;
			//have every player perform an action
			for (PokerPlayer player : preflopQueue) {
				scanner = new Scanner(System.in);
				double chips = performAction(player, scanner, bet, pot, preflopAction, flopAction, turnAction, riverAction);
				if (chips == bet) {
					preflopAction.add("Player " + player.ID + "called " + bet);
					postflopQueue.add(player);
				}
				else if (chips >bet) {
					preflopAction.add("Player " + player.ID + "raised to " + chips);
					postflopQueue.add(player);
					bet = chips;
				}
				else {
					preflopAction.add("Player " + player.ID + "folded");
				}
				pot += chips;
				player.addChips(-chips);
				
			}
			scanner.close();
			postflopQueue = getPostflop(postflopQueue);
			
			//flop time
			System.out.print("The flop is: ");
			for(int i = 0; i < 3; i++) {
				communityCards[i] = deck.draw();
				System.out.print(communityCards[i] + " ");
			}
			System.out.println();
			
			
			break;
		}
	}
	
	public static int getStartingPlayer(List<PokerPlayer> players, PlayingDeck deck) {
		PlayingCard highest = new PlayingCard(-1, 'c');
		int player = -1;
		for(int p = 0; p < players.size(); p++) {
			
			PlayingCard pulled = deck.draw();
			pulled.value = (pulled.value + 15) % 17; //Aces = 1 but would be the highest so we need to change things
			
			if (pulled.value == highest.value) {
				if(pulled.suit == 's' || highest.suit == 'd') {
					highest = pulled;
					player = p;
				} else if(pulled.suit == 'd' || highest.suit == 's') {
					continue;
				} else {
					if (pulled.suit == 'c'){
						highest = pulled;
						player = p;
					}
				}
			} 
			else if(pulled.value > highest.value) {
				highest = pulled;
				player = p;
			}
		}
		
		//This would be who gets to be dealer, so the player first to act will be dealer + 3
		player += 3;
		int i = 0;
		while(i < player) {
			PokerPlayer p = players.remove(0);
			players.add(p);
			i++;
		}
		
		return player;
		
	}
	
	public static Queue<PokerPlayer> getPostflop(Queue<PokerPlayer> queue) {
		int num_players = queue.size();
		PokerPlayer[] positions = new PokerPlayer[num_players];
		
		for(int i = 0; i < num_players; i++) {
			PokerPlayer player = queue.remove();
			positions[(i+2) % (num_players)] = player;
			queue.add(player);
		}
		Queue<PokerPlayer> postflopQueue = new ArrayDeque<PokerPlayer>(); 
		for(PokerPlayer player : positions) {
			postflopQueue.add(player);
		}
		
		return postflopQueue;
		
		
	}
	
	public static void movePosition(Queue<PokerPlayer> preflop, Queue<PokerPlayer>postflop) {
		int num_players = preflop.size();
		PokerPlayer[] prepositions = new PokerPlayer[num_players];
		PokerPlayer[] postpositions = new PokerPlayer[num_players];
		
		for(int i = 0; i < num_players; i++) {
			PokerPlayer preflopPlayer = preflop.remove();
			
			PokerPlayer postflopPlayer = postflop.remove();
			prepositions[(i+1) % (num_players)] = preflopPlayer;
			
			//update the prePosition
			preflopPlayer.prePosition += 1;
			preflopPlayer.prePosition %= num_players;
			postpositions[(i+1) % (num_players)] = postflopPlayer;
		}
		
		for(int i = 0; i < num_players; i++) {
			preflop.add(prepositions[i]);
			postflop.add(postpositions[i]);
		}
	}
	
	public static double performAction(PokerPlayer player, 
									   Scanner scanner, 
									   double bet, double pot, 
									   Queue<String> preflop, 
									   Queue<String> flop, 
									   Queue<String> turn, 
									   Queue<String> river 
	){
		
		int validMove = 0;
		while(validMove == 0) {
			System.out.print("Player " + player.ID + " it is your turn. ");
			System.out.println("You have: " + player + " and your position is: " + switchPositions(player.prePosition));
			System.out.println("It will cost " + bet + " to call");
			System.out.println("(F)old, (C)all, (R)aise, (I)nformation");
			
			String action = scanner.nextLine();
			action = action.toLowerCase();
			
			switch(action.charAt(0)) {
			case 'f':
				System.out.println("You have folded");
				validMove = 1;
				return 0.0;
			case 'c':
				System.out.println("You have called");
				validMove = 1;
				return bet;
			case 'r':
				
				int validRaise = 0;
				double raiseAmount = 0;
				while(validRaise == 0) {
					System.out.println("How much would you like to raise? (Min: " + bet*2 + ")");
					raiseAmount = scanner.nextDouble();
					if(raiseAmount >= bet * 2) {
						validRaise =1;
					}
					
				}
				return raiseAmount;
				
			case 'i':
				System.out.println("There is " + pot + " in the pot");
				System.out.println("You have " + player.chips + " chips\n");
				if(preflop.size() != 0) {
					System.out.println("Preflop action: ");
					for(String prevAction : preflop) {
						System.out.println(prevAction);
					}
					
				}
				
				if(flop.size() != 0) {
					System.out.println("flop action: ");
					for(String prevAction : flop) {
						System.out.println(prevAction);
					}
					System.out.println();
				}
				
				if(turn.size() != 0) {
					System.out.println("Turn action: ");
					for(String prevAction : turn) {
						System.out.println(prevAction);
					}
					System.out.println();
				}
				
				if(river.size() != 0) {
					System.out.println("River action: ");
					for(String prevAction : river) {
						System.out.println(prevAction);
					}
					System.out.println();
				}
				break;
			}
		}
		return 0.0;
			
	}
	
	public static String switchPositions(int position) {
		String[] num_to_position = new String[9];
		num_to_position[0] = "UTG";
		num_to_position[1] = "UTG+1";
		num_to_position[2] ="UTG+2";
		num_to_position[3] ="HJ";
		num_to_position[4] ="LJ";
		num_to_position[5] ="CO";
		num_to_position[6] ="Button";
		num_to_position[7] ="SB";
		num_to_position[8] ="BB";
		return num_to_position[position];
	}
	
	
}
