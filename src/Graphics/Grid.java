package Graphics;

import Algorithm.Pathfinding;
import Algorithm.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Grid extends JPanel implements MouseListener
{
	public static final int squareSize = 20;
	public static int width = 10;
	public static int height = 10;

	private Vertex[][] grid;
	private Vertex start;
	private Vertex finish;

	public Grid(int obstaclesChance)
	{
		grid = new Vertex[width + 2][height + 2];
		Random rnd = new Random();

		for (int i = 0; i < height + 2; i++)
		{
			grid[0][i] = new Vertex(0, i * squareSize); // settings left wall
			grid[0][i].isObstacle = true;
			grid[width + 1][i] = new Vertex((width + 1) * squareSize, i * squareSize); // settings right wall
			grid[width + 1][i].isObstacle = true;
		}

		for (int i = 1; i < width + 1; i++)
		{
			grid[i][0] = new Vertex(i * squareSize, 0); // settings upper wall
			grid[i][0].isObstacle = true;
			grid[i][height + 1] = new Vertex(i * squareSize, (height + 1) * squareSize); // settings lower wall
			grid[i][height + 1].isObstacle = true;
		}


		for (int i = 1; i <= width; i++) //Creating field vertices
		{
			for (int j = 1; j <= height; j++)
			{
				grid[i][j] = new Vertex(i * squareSize, j * squareSize);
				if (rnd.nextInt(100) < obstaclesChance) // creating an obstacle
				{
					grid[i][j].isObstacle = true;
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for (Vertex[] vertices : grid)
		{
			for (Vertex vertex : vertices)
			{
				if (vertex.isObstacle)
				{
					g.drawRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
					g.setColor(Color.BLACK);
					g.fillRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
				}
				else
				{
					g.drawRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
				}
			}
		}

		g.setColor(Color.GREEN);
		g.fillRect(start.getX(), start.getY(), squareSize, squareSize);
		g.setColor(Color.RED);
		g.fillRect(finish.getX(), finish.getY(), squareSize, squareSize);

		if (finish.getParent() != null)
		{
			Vertex tmp = finish.getParent();
			g.setColor(Color.YELLOW);
			while (tmp != null || tmp != start)
			{
				g.fillRect(tmp.getX(), tmp.getY(), squareSize, squareSize);
				tmp = tmp.getParent();
			}
		}
	}

	public Vertex getElementOn(double x, double y)
	{
		for (int i = 0; i <= height + 1; i++)
			for (int j = 0; j <= width + 1; j++)
				if (grid[i][j].distanceTo(new Vertex(x, y)) < squareSize - 1)
					return grid[i][j];

		return null;
	}

	public void getNeighbours(Vertex element)
	{
		ArrayList<Vertex> neighbours = new ArrayList<>();

		double leftUpperX = element.getX() - squareSize;
		double leftBottomX = element.getX() + squareSize;
		double leftUpperY = element.getY() - squareSize;

		for (int i = (int) leftUpperY; i < 3 * squareSize + leftUpperY; i += squareSize) //add upper and lower neighbours
		{
			neighbours.add(getElementOn(leftUpperX, i));
			neighbours.add(getElementOn(leftBottomX, i));
		}

		neighbours.add(getElementOn(element.getX() - squareSize, element.getY()));
		neighbours.add(getElementOn(element.getX() + squareSize, element.getY()));

		element.setNeighbours(neighbours);
	}

	public void setStart(int x, int y)
	{
		start = getElementOn(x * squareSize + squareSize, y * squareSize + squareSize);
	}

	public void setFinish(int x, int y)
	{
		finish = getElementOn(x * squareSize + squareSize, y * squareSize + squareSize);
	}

	public void getShortestPath()
	{
		for (Vertex[] vertices : grid)
		{
			for (Vertex vertex : vertices)
			{
				getNeighbours(vertex);
			}
		}
		Pathfinding.getShortestPath(start, finish);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(Window.ctrlPressed && new Rectangle(squareSize, squareSize, width * squareSize, height * squareSize).contains(e.getX(), e.getY()))
		{
			setStart(e.getX() - 16/* / squareSize - squareSize*/, (e.getYOnScreen() /*- 2 * squareSize) / squareSize*/ - 39));
			repaint();
		}
	}

	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}
