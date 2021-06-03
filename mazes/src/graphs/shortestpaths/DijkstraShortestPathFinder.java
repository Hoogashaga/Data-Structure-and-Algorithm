package graphs.shortestpaths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;



/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> distTo = new HashMap<>();
        HashSet<V> visited = new HashSet<>();
        ExtrinsicMinPQ<V> perimeter = createMinPQ();

        if (Objects.equals(start, end)) {
            return edgeTo;
        }

        distTo.put(start, 0.0);
        perimeter.add(start, 0.0);

        while (!perimeter.isEmpty()) {
            V from = perimeter.removeMin();
            if (visited.contains(from)) {
                continue;
            } else {
                visited.add(from);
            }

            if (visited.contains(end)) {
                return edgeTo;
            }

            for (E edge : graph.outgoingEdgesFrom(from)) {
                V to = edge.to();
                if (!visited.contains(to)) {
                    if (perimeter.contains(to)
                        && (perimeter.contains(to) && ((distTo.get(from) + edge.weight()) < distTo.get(to)))) {
                        edgeTo.put(to, edge);
                        distTo.put(to, distTo.get(from) + edge.weight());
                        perimeter.changePriority(to, distTo.get(from) + edge.weight());
                    } else if (!perimeter.contains(to)) {
                        edgeTo.put(to, edge);
                        distTo.put(to, distTo.get(from) + edge.weight());
                        perimeter.add(to, distTo.get(from) + edge.weight());
                    }
                    }
                }
            }
        if (perimeter.isEmpty()) {
            new ShortestPath.Failure<>();
        }
        return edgeTo;
    }


    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        List<E> path = new ArrayList<>();

        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        E edge = spt.get(end);
        if (edge == null) {
            return new ShortestPath.Failure<>();
        }

        while (!Objects.equals(edge.from(), start)) {
            path.add(edge);
            edge = spt.get(edge.from());
        }
        path.add(edge);
        Collections.reverse(path);

        return new ShortestPath.Success<>(path);
    }

}
