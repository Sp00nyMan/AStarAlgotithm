package Graphics;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener
{
	Grid field;
	public static boolean ctrlPressed = false;

	public Window(int startX, int startY, int finishX, int finishY, int obstacleChance)
	{
		setTitle("A*");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(40, 40, (Grid.width + 2) * Grid.squareSize + 16, (Grid.height + 2) * Grid.squareSize + 39);
		field = new Grid(obstacleChance);
		field.setStart(startX, startY);
		field.setFinish(finishX, finishY);

		add(field);
		addMouseListener(field);
		addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		if(!ctrlPressed && e.getKeyCode() == KeyEvent.VK_CONTROL)
			{
				ctrlPressed = true;
				System.out.println("CTRL PRESSED");
			}
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			ctrlPressed = false;
			System.out.println("CTRL RELEASED");
		}
	}

	public void keyTyped(KeyEvent e){}
}
