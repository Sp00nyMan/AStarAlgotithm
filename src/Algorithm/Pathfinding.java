package Algorithm;

import java.util.ArrayList;

public class Pathfinding
{
	public static void getShortestPath(Vertex start, Vertex finish)
	{
		ArrayList<Vertex> toTest = new ArrayList<>();
		toTest.add(start);

		while (!toTest.isEmpty())
		{
			toTest.sort((o1, o2) -> (int) (o1.getScore() - o2.getScore()));
		}
	}
}
