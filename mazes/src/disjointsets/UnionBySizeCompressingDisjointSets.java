package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    private HashMap<T, Integer> ids;
    private int size;
    private int initial;



    public UnionBySizeCompressingDisjointSets() {
        this.pointers = new ArrayList<Integer>();
        this.initial = -5;
        this.size = 0;
        this.ids = new HashMap<T, Integer>();
    }

    @Override
    public void makeSet(T item) {
        if (this.ids.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        this.pointers.add(this.initial);
        this.ids.put(item, this.size);
        this.size++;
    }

    @Override
    public int findSet(T item) {
        if (!this.ids.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        int index = this.ids.get(item);
        return findSetHelper(index);
    }

    private int findSetHelper(int index) {
        if (this.pointers.get(index) < 0) {
            return index;
        }
        return findSetHelper(this.pointers.get(index));
    }

    @Override
    public boolean union(T item1, T item2) {
        if (!ids.containsKey(item1) || !ids.containsKey(item2)) {
            throw new IllegalArgumentException();
        }
        if (findSet(item1) == findSet(item2)) {
            return false;
        }

        int p1 = this.pointers.get(findSet(item1));
        int p2 = this.pointers.get(findSet(item2));

        if (p1 >= p2) {
            this.pointers.set(findSet(item2), this.pointers.get(findSet(item1)) + this.pointers.get(findSet(item2)));
            this.pointers.set(findSet(item1), findSet(item2));
        } else {
            this.pointers.set(findSet(item1), this.pointers.get(findSet(item1)) + this.pointers.get(findSet(item2)));
            this.pointers.set(findSet(item2), findSet(item1));
        }
        return true;
    }
}
