package Graphics;

import Algorithm.Pathfinder;
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
	public static int width = 30;
	public static int height = 30;
	public static boolean onlyShortest = false;
	public static boolean allowDiagonals = false;

	private Vertex[][] grid;
	private Vertex start;
	private Vertex finish;
	private boolean pathFound = false;

	public Grid(int obstaclesChance) {
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

		int rndX = rnd.nextInt(width - 1) + 1;
		int rndY = rnd.nextInt(height - 1) + 1;
		setStart(rndX, rndY);

		rndX = rnd.nextInt(width - 1) + 1;
		rndY = rnd.nextInt(height - 1) + 1;
		setFinish(rndX, rndY);
	}

	public void getShortestPath() {

		pathFound = true;
		resetGrid();
		for (int i = 1; i <= height; i++)
		{
			for (int j = 1; j <= width; j++)
			{
				getNeighbours(grid[i][j]);
			}
		}
		Pathfinder.getShortestPath(start, finish, onlyShortest);
	}
	public void getNeighbours(Vertex element) {
		ArrayList<Vertex> neighbours = new ArrayList<>();

		neighbours.add(getElementOn(element.getX(), element.getY() - squareSize)); // upper element
		neighbours.add(getElementOn(element.getX() + squareSize, element.getY())); // right element
		neighbours.add(getElementOn(element.getX() - squareSize, element.getY())); // left element
		neighbours.add(getElementOn(element.getX(), element.getY() + squareSize)); // lower element

		if(allowDiagonals)
		{
			neighbours.add(getElementOn(element.getX() - squareSize, element.getY() - squareSize)); // upperLeft element
			neighbours.add(getElementOn(element.getX() + squareSize, element.getY() - squareSize)); // upperRight element
			neighbours.add(getElementOn(element.getX() + squareSize, element.getY() + squareSize)); // lowerRight element
			neighbours.add(getElementOn(element.getX() - squareSize, element.getY() + squareSize)); // lowerLeft element
		}
		neighbours.removeIf(neighbour -> neighbour.isObstacle); // remove obstacles
		element.setNeighbours(neighbours);
	}

	@Override
	public void paintComponent(Graphics g) {
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
					if (/*!onlyShortest && */vertex.isVisited)
					{
						g.drawRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
						g.setColor(Color.BLUE);
						g.fillRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
						g.setColor(Color.BLACK);
					}
					else
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
			while (tmp != null && tmp != start)
			{
				g.fillRect(tmp.getX(), tmp.getY(), squareSize, squareSize);
				tmp = tmp.getParent();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (new Rectangle(squareSize, squareSize, width * squareSize + squareSize, height * squareSize + squareSize).contains(e.getX() - 7, e.getY() - 30))
		{

			int xpos = (e.getX() - 7 - squareSize) / squareSize;
			int ypos = (e.getY() - 30 - squareSize) / squareSize;

			if(e.isControlDown())
			{
				switch (e.getButton())
				{
					case MouseEvent.BUTTON1:
						setObstacle(xpos, ypos); //ctrl + lmb - new obstacle
						break;
					case MouseEvent.BUTTON3:
						removeObstacle(xpos, ypos); //ctrl + rmb - remove obstacle
						break;
				}
			}
			else
				switch (e.getButton())
				{
					case MouseEvent.BUTTON1:
						setStart(xpos, ypos); // lmb - move start
						break;
					case MouseEvent.BUTTON3:
						setFinish(xpos, ypos);
						break;
				}

			if(pathFound) //if grid changed rebuild path
				getShortestPath();
			repaint();
		}
	}

	public Vertex getElementOn(double x, double y) {
		for (int i = 0; i <= height + 1; i++)
			for (int j = 0; j <= width + 1; j++)
				if (grid[i][j].distanceTo(new Vertex(x, y)) < squareSize - 1)
					return grid[i][j];

		return null;
	}
	public void setStart(int x, int y) {
		Vertex element = getElementOn(x * squareSize + squareSize, y * squareSize + squareSize);
		if (element != start && element != finish)
		{
			if (start != null)
			{
				start.setDistanceFromStart(Double.POSITIVE_INFINITY);
				resetGrid();
			}
			start = element;
			start.isObstacle = false;
			start.setDistanceFromStart(-squareSize / 2.0);
		}
	}
	public void setFinish(int x, int y) {
		Vertex element = getElementOn(x * squareSize + squareSize, y * squareSize + squareSize);
		if (element != start && element != finish)
		{
			if(finish != null)
				resetGrid();
			finish = element;
			finish.isObstacle = false;
		}
	}
	public void setObstacle(int x, int y) {
		Vertex element = getElementOn(x * squareSize + squareSize, y * squareSize + squareSize);
		if (element != start && element != finish)
		{
			resetGrid();
			element.isObstacle = true;
		}
	}
	public void removeObstacle(int x, int y)
	{
		Vertex element = getElementOn(x * squareSize + squareSize, y * squareSize + squareSize);
		if (element.isObstacle)
		{
			resetGrid();
			element.isObstacle = false;
		}
	}
	public void resetGrid()
	{
		for (int i = 1; i <= height; i++)
		{
			for (int j = 1; j <= width; j++)
			{
				grid[i][j].isVisited = false;
				grid[i][j].setParent(null);
				grid[i][j].setDistanceFromStart(Double.POSITIVE_INFINITY);
				grid[i][j].setScore(Double.NEGATIVE_INFINITY);
			}
		}
		start.setDistanceFromStart(0);
	}


	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
