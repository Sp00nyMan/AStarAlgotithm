package Graphics;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener
{
	Grid field;

	public Window(int obstacleChance) {
		setTitle("A*");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500, 40, (Grid.width + 2) * Grid.squareSize + 15, (Grid.height + 2) * Grid.squareSize + 38);
		field = new Grid(obstacleChance);

		add(field);
		addMouseListener(field);
		addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			field.getShortestPath();
			repaint();
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
