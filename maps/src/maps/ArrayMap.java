package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    private int size;
    private int initialCapacity;
    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.size = 0;
        this.initialCapacity = initialCapacity;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }



    @Override
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
            if (key == null && entries[i].getKey() == null) {
                return entries[i].getValue();
            }
            else if (entries[i].getKey() == key || entries[i].getKey().equals(key)) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size == initialCapacity) {
            initialCapacity = initialCapacity * 2;
            SimpleEntry<K, V>[] newArray = this.createArrayOfEntries(initialCapacity);
            for (int i = 0; i < size; i++) {
                newArray[i] = new SimpleEntry<>(entries[i].getKey(), entries[i].getValue());
            }
            entries = newArray;
        }
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == null && key == null) {
                    entries[i] = new SimpleEntry<>(entries[i].getKey(), value);
                    return null;
            }
            else if (entries[i].getKey().equals(key)) {
                V tmp = entries[i].getValue();
                entries[i] = new SimpleEntry<>(entries[i].getKey(), value);
                return tmp;
            }
        }
        entries[size] = new SimpleEntry<>(key, value);
        size++;
        return null;
    }

    @SuppressWarnings("checkstyle:EmptyBlock")
    @Override
    public V remove(Object key) {
        for (int i = 0; i < size; i++) {
            if ((key == null && entries[i].getKey() == null) ||
                (entries[i].getKey() == key || entries[i].getKey().equals(key))) {
                V tmp = entries[i].getValue();
                entries[i] = new SimpleEntry<>(entries[size - 1].getKey(), entries[size - 1].getValue());
                size--;
                return tmp;
            }
        }
        return null;
    }
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() != null || entries[i].getValue() != null) {
                entries[i] = null;
                }
            }
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey() == null) {
                return key == null;
            }
            else if (entries[i].getKey() == key || entries[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries, this.size());
    }

    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        // private SimpleEntry<K, V> cur;
        int i;
        int pairsSize;



        public ArrayMapIterator(SimpleEntry<K, V>[] entries, int size) {
            this.entries = entries;

            this.i = 0;
            this.pairsSize = size;
        }

        @Override
        public boolean hasNext() {
            return !(this.i >= this.pairsSize);
        }

        @Override
        public Entry<K, V> next() {
            if (this.hasNext()) {
                SimpleEntry<K, V> tmp = entries[i];
                i++;
                return tmp;
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }
}


