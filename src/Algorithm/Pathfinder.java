package Algorithm;


import java.util.Stack;

public class Pathfinder
{
	public static void getShortestPath(Vertex start, Vertex finish, boolean onlyShortest) {
		Stack<Vertex> toTest = new Stack<>();
		toTest.push(start);

		while (!toTest.isEmpty())
		{
			if(!onlyShortest && finish.getParent() != null)
				break;
			toTest.sort((o1, o2) -> (int) (o2.getScore() - o1.getScore()));

			toTest.removeIf(vertex -> vertex.isVisited);
			if (toTest.isEmpty())
				break;
			Vertex current = toTest.peek();
			current.isVisited = true;
			for (Vertex neighbour : current.getNeighbours())
			{
				if (!neighbour.isVisited)
				{
					toTest.push(neighbour);
				}

				double lowerDistanceFromStart = neighbour.calculateDistanceFromStart(current);

				if(lowerDistanceFromStart < neighbour.getDistanceFromStart())
				{
					neighbour.setParent(current);
					neighbour.setDistanceFromStart(lowerDistanceFromStart);

					neighbour.setScore(neighbour.calculateScore(finish));
				}
			}
		}
	}
}
