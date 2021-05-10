package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;
    HashMap<T, Integer> map;
    int heapSize;



    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        // items.set(START_INDEX, null);
        map = new HashMap<>();
        heapSize = START_INDEX;
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> n1 = items.get(a);
        PriorityNode<T> n2 = items.get(b);
        items.set(a, n2);
        items.set(b, n1);
        map.put(n1.getItem(), b);
        map.put(n2.getItem(), a);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items.add(new PriorityNode<T>(item, priority));
        map.put(item, heapSize);
        heapSize++;
        shiftUp(items.size() - 1);
    }

    @Override
    public boolean contains(T item) {
        if (size() == 0) {
            return false;
        }
        return map.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (items.size() == 0) {
            throw new NoSuchElementException();
        }
        return items.get(0).getItem();
    }

    @Override
    public T removeMin() {
        if (items.size() == 0) {
            throw new NoSuchElementException();
        }
        T minRemoved = items.get(0).getItem();
        swap(0, size() - 1);
        items.remove(size() - 1);
        map.put(minRemoved, 0);
        map.remove(minRemoved);
        shiftDown(0);
        return minRemoved;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = map.get(item);
        double oldPriority = items.get(index).getPriority();
        if (priority > oldPriority) {
            items.get(index).setPriority(priority);
            shiftDown(index);
        } else {
            items.get(index).setPriority(priority);
            shiftUp(index);
        }
    }

    @Override
    public int size() {
        return items.size();
    }

    private void shiftUp(int index) {
        int parent;
        if (index == 0) {
            parent = 0;
        } else {
            parent = (index - 1) / 2;
        }
        if (items.get(index).getPriority() < items.get(parent).getPriority()) {
            swap(index, parent);
            shiftUp(parent);
        }
    }

    private void shiftDown(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;
        int smallestIndex = index;
        if (leftChild < size()) {
            smallestIndex = leftChild;
            }
        if (rightChild < size()  && items.get(smallestIndex).getPriority() > items.get(rightChild).getPriority()) {
            smallestIndex = rightChild;
            }

        if (smallestIndex != index) {
            if (items.get(index).getPriority() > items.get(smallestIndex).getPriority()) {
                swap(index, smallestIndex);
                shiftDown(smallestIndex);
                }
        }
    }
}
