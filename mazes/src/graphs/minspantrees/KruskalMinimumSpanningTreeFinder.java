package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        // return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @SuppressWarnings("checkstyle:NeedBraces")
    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        DisjointSets<V> disjointSets = createDisjointSets();

        if (edges.size() == 0) {
            return new MinimumSpanningTree.Success<>();
        }
        int n = 0;
        Set<E> mst = new HashSet<>();
        if (graph.allVertices().size() == 0) {
            return new MinimumSpanningTree.Success<>();
        }
        if (graph.allVertices().size() == 2 && edges.size() == 0) {
                return new MinimumSpanningTree.Failure<>();
            }

        for (V v: graph.allVertices()) {
            disjointSets.makeSet(v);
            n++;
        }
        for (E edge : edges) {
            V v1 = edge.from();
            V v2 = edge.to();
            if (disjointSets.findSet(v1) != disjointSets.findSet(v2)) {
                disjointSets.union(v1, v2);
                mst.add(edge);
            }
        }
        if (mst.size() == (n - 1)) {
            return new MinimumSpanningTree.Success<>(mst);
        } else {
            return new MinimumSpanningTree.Failure<>();
        }
    }
    //
}
