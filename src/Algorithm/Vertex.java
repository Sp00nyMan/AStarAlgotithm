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
	private double score = Double.NEGATIVE_INFINITY;

	public boolean isObstacle = false;
	public boolean isVisited = false;

	public Vertex(double x, double y) {
		this.coords = new Point2D.Double(x, y);
	}


	public double distanceTo(Vertex other) {
		return coords.distance(other.coords);
	}
	public double calculateDistanceFromStart(Vertex parentVertex) {
		return parentVertex.getDistanceFromStart() + Grid.squareSize;
	}
	public double calculateScore(Vertex finishVertex) {
		return getDistanceFromStart() + distanceTo(new Vertex(finishVertex.getX() + Grid.squareSize / 2.0, finishVertex.getY() + Grid.squareSize / 2.0));
	}



	public void setNeighbours(ArrayList<Vertex> neighbours) {
		this.neighbours = new ArrayList<>();
		this.neighbours.addAll(neighbours);
	}
	public void setParent(Vertex parent){
		this.parent = parent;
	}
	public void setDistanceFromStart(double distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}
	public void setScore(double score)
	{
		this.score = score;
	}


	public ArrayList<Vertex> getNeighbours() {
		return neighbours;
	}
	public Vertex getParent() {
		return parent;
	}
	public double getScore() {
		return distanceFromStart + score;
	}
	public int getX() {
		return (int) coords.getX();
	}
	public int getY() {
		return (int) coords.getY();
	}
	public double getDistanceFromStart() {
		return distanceFromStart;
	}
}
