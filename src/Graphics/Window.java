package Graphics;

import Algorithm.Grid;
import Algorithm.Vertex;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener
{
	Grid field;
	Vertex starPoint;
	Vertex finishPoint;


	public Window(int startX, int startY, int finishX, int finishY, int obstacleCance)
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		field = new Grid(obstacleCance);
		starPoint = new Vertex(startX, startY);
		finishPoint = new Vertex(finishX, finishY);
		setBounds(40, 40, (Grid.width + 2) * Grid.squareSize + 16, (Grid.height + 2) * Grid.squareSize + 39);

		add(field);
		addKeyListener(this);
		setVisible(true);
	}

	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}
