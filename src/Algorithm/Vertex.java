package Algorithm;

import Graphics.Grid;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Vertex
{
	private Point2D.Double coords;
	private ArrayList<Vertex> neighbours;
	private Vertex parent = null;

	private double distanceFromStart = Double.POSITIVE_INFINITY;
	private double distanceToEnd = Double.POSITIVE_INFINITY;

	public boolean isObstacle = false;

	public Vertex(Point2D.Double coods)
	{
		this.coords = new Point2D.Double(coods.x, coods.y);
		neighbours = new ArrayList<>();
	}
	public Vertex(double x, double y)
	{
		this.coords = new Point2D.Double(x, y);
	}
	public Vertex(Vertex other)
	{
		this.coords = new Point2D.Double(other.coords.x, other.coords.y);
		this.neighbours = new ArrayList<>();

		neighbours.addAll(other.neighbours);
	}

	public double distanceTo(Vertex other)
	{
		return coords.distance(other.coords);
	}

	public void calculateDistances(Vertex parentVertex, Vertex finishVertex)
	{
		parent = parentVertex;
		distanceFromStart = parentVertex.distanceFromStart + Grid.squareSize;
		distanceToEnd = distanceTo(new Vertex(finishVertex.getX() + Grid.squareSize / 2.0, finishVertex.getY() + Grid.squareSize / 2.0));
	}

	public void setNeighbours(ArrayList<Vertex> neighbours)
	{
		this.neighbours = new ArrayList<>();
		this.neighbours.addAll(neighbours);
		System.out.println();
	}
	public ArrayList<Vertex> getNeighbours()
	{
		return neighbours;
	}
	public Vertex getParent()
	{
		return parent;
	}
	public double getScore()
	{
		return distanceFromStart + distanceToEnd;
	}

	public int getX()
	{
		return (int) coords.getX();
	}
	public int getY()
	{
		return (int) coords.getY();
	}
}
