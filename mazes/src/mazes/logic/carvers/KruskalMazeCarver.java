package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;
import java.util.stream.Collectors;



import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        // Hint: you'll probably need to include something like the following:
        // this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));
        // Random rand = new Random();
        Set<Wall> res = new HashSet<>();
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> minesTree = null;

        List<EdgeWithData<Room, Wall>> edges = walls.stream()
            .map(wall -> new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(), this.rand.nextDouble(), wall))
            .collect(Collectors.toList());

        // minesTree = this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));
        for (EdgeWithData<Room, Wall> s :
            this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges)).edges()) {
            res.add(s.data());
        }
        return res;
    }
}
