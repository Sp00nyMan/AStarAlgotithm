package Algorithm;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Grid extends JPanel
{
	public static final int squareSize = 20;
	public static int width = 10;
	public static int height = 10;

	Vertex[][] grid;

	public Grid(int obstaclesChance)
	{
		grid = new Vertex[width + 2][height + 2];
		Random rnd = new Random();

		for (int i = 0; i < height  + 2; i++)
		{
			grid[0][i] = new Vertex(0, i * squareSize); // settings left wall
			grid[0][i].isObstacle = true;
			grid[width + 1][i] = new Vertex((width + 1) * squareSize, i * squareSize); // settings right wall
			grid[width + 1][i].isObstacle = true;
		}

		for (int i = 1; i < width  + 1; i++)
		{
			grid[i][0] = new Vertex(i * squareSize, 0); // settings upper wall
			grid[i][0].isObstacle = true;
			grid[i][height + 1] = new Vertex(i * squareSize, (height + 1) * squareSize); // settings lower wall
			grid[i][height + 1].isObstacle = true;
		}


		for (int i = 1; i <= width; i++)
		{
			for (int j = 1; j <= height; j++)
			{
				grid[i][j] = new Vertex(i * squareSize, j * squareSize);
				if(rnd.nextInt(100) < obstaclesChance)
				{
					//grid[i][j].isObstacle = true;
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

				if(vertex.isObstacle)
				{
					g.setColor(Color.BLACK);
					g.fillRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
				}
				else
				{
					g.setColor(Color.BLACK);
					g.drawRect(vertex.getX(), vertex.getY(), squareSize, squareSize);
				}
			}
		}
	}
}
