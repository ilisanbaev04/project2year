package Solitaire;

public class Cards {
	private Suits suit;
	private Value value;
	private Color color;
	private boolean visable;

	private void setColor() {
		switch (suit) {
			case Hearts:
				this.color = Color.red;
				break;
			case Spades:
				this.color = Color.black;
				break;
			case Clubs:
				this.color = Color.black;
				break;
			case Diamonds:
				this.color = Color.red;
				break;
			default:
				System.out.println("ERROR: could not set card color!");
		}
	}

	public Cards() {
		this.suit = Suits.Spades;
		this.value = Value.Ace;
		setColor();
		this.visable = false;
	}

	public Cards(Suits suit, Value value, boolean visable) {
		this.suit = suit;
		this.value = value;
		setColor();
		this.visable = visable;
	}

	public Cards(Suits suit, Value value) {
		this.suit = suit;
		this.value = value;
		setColor();
		this.visable = false;
	}

	public void setVisability(boolean visable) {
		this.visable = visable;
	}

	public Suits getSuit() {
		return this.suit;
	}

	public Value getValue() {
		return this.value;
	}

	public Color getColor() {
		return this.color;
	}

	public boolean getVisibility() {
		return this.visable;
	}
}
