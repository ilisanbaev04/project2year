package Solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Cards> cards;

	public Deck() {
		cards = new ArrayList<>();
		for (Suits suit : Suits.values()) {
			for (Value value : Value.values()) {
				cards.add(new Cards(suit, value));
			}
		}
	}

	public void Shuffle() {
		Collections.shuffle(cards);
	}

	public Cards getCard(int index) {
		return cards.get(index);
	}

	public void setCardVisibility(int index, boolean visibility) {
		cards.get(index).setVisability(visibility);
	}

	public boolean getCardVisibility(int index) {
		return cards.get(index).getVisibility();
	}

	public Color getCardColor(int index) {
		return cards.get(index).getColor();
	}
}
