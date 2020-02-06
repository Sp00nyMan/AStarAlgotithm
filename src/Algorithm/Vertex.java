package Algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Vertex
{
	private Point coords;
	private ArrayList<Vertex> neighbours;

	private double distanceFromStart;
	private double distanceToEnd;

	public boolean isObstacle = false;

	public Vertex(Point coods)
	{
		this.coords = new Point(coods);
		neighbours = new ArrayList<>();
	}
	public Vertex(double x, double y)
	{
		this.coords = new Point((int)x, (int)y);
	}
	public Vertex(Vertex other)
	{
		this.coords = new Point(other.coords);
		this.neighbours = new ArrayList<>();

		neighbours.addAll(other.neighbours);
	}

	public double distanceTo(Vertex other)
	{
		return coords.distance(other.coords);
	}

	public void calculateDistances(Vertex start, Vertex finish)
	{
		distanceFromStart = start.distanceTo(this);
		distanceToEnd = distanceTo(finish);
	}

	public void setNeighbours(Vertex[] neighbours)
	{
		this.neighbours.addAll(Arrays.asList(neighbours));
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
