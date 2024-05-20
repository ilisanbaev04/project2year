package GUI;

import Solitaire.Cards;

import javax.swing.*;
import java.awt.*;

public class CardComponent extends JComponent {
    private Cards card;

    public CardComponent(Cards card) {
        this.card = card;
        setPreferredSize(new Dimension(100, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (card.getVisibility()) {
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, 100, 150);
            g.setColor(java.awt.Color.BLACK);
            g.drawRect(0, 0, 100, 150);
            g.setColor(getAWTColor(card.getColor()));
            g.drawString(card.getValue().toString(), 10, 20);
            g.drawString(card.getSuit().toString(), 10, 40);
        } else {
            g.setColor(java.awt.Color.BLUE);
            g.fillRect(0, 0, 100, 150);
            g.setColor(java.awt.Color.BLACK);
            g.drawRect(0, 0, 100, 150);
        }
    }

    private java.awt.Color getAWTColor(Solitaire.Color color) {
        switch (color) {
            case red:
                return java.awt.Color.RED;
            case black:
                return java.awt.Color.BLACK;
            default:
                return java.awt.Color.BLACK; // Default to black if color is unknown
        }
    }
}
