package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 3;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 4;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 5;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    private int size;
    private int icount;
    private int capacity;
    private double resizeloader;

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.size = 0;
        this.capacity = chainInitialCapacity;
        this.icount = initialChainCount;
        this.resizeloader = resizingLoadFactorThreshold;
        this.chains = createArrayOfChains(initialChainCount);
        for (int i = 0; i < initialChainCount; i++) {
            chains[i] = createChain(chainInitialCapacity);
        }
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        int hashcode;
        if (key != null) {
            hashcode = Math.abs(key.hashCode() % chains.length);
        } else {
            hashcode = 0;
        }

        if (chains[hashcode] == null || !containsKey(key)) {
            return null;
        } else {
            return chains[hashcode].get(key);
        }
    }


    @Override
    public V put(K key, V value) {
        int hashcode;
        if (key != null) {
            hashcode = Math.abs(key.hashCode()) % chains.length;
        } else {
            hashcode = 0;
        }
        if (containsKey(key)) {
            return chains[hashcode].put(key, value);
        }
        if (chains[hashcode] == null) {
            chains[hashcode] = new ArrayMap<>();
        }
        chains[hashcode].put(key, value);
        size++;

        if ((double) size / (double) chains.length >= resizeloader) {
            int newchainslength = chains.length * 2;
            AbstractIterableMap<K, V>[] newChains = createArrayOfChains(newchainslength);
            int length = newChains.length;
            for (Map.Entry<K, V> item : this) {
                int newHash = item.getKey().hashCode();
                int mod = newHash % length;
                if (newChains[mod] == null) {
                    newChains[mod] = new ArrayMap<>();
                }
                newChains[mod].put(item.getKey(), item.getValue());
            }
            chains = newChains;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int hashcode;
        if (key != null) {
            hashcode = Math.abs(key.hashCode() % chains.length);
        } else {
            hashcode = 0;
        }
        if (chains[hashcode].containsKey(key)) {
            V value = chains[hashcode].remove(key);
            size--;
            return value;
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        this.chains = createArrayOfChains(chains.length);
        for (int j = 0; j < chains.length; j++) {
            if (chains[j] != null) {
                chains[j] = null;
            }
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hashcode;
        boolean res;
        if (key != null) {
            hashcode = Math.abs(key.hashCode() % chains.length);
        } else {
            hashcode = 0;
        }
        res = chains[hashcode].containsKey(key);
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }


    // Doing so will give you a better string representation for assertion errors the debugger.
    // @Override
    // public String toString() {
    //     return super.toString();
    // }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        // You may add more fields and constructor parameters
        private int curChain;
        private Iterator<Map.Entry<K, V>> curIterator;

        @SuppressWarnings("checkstyle:ParenPad")
        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.curChain = 0;
            if (this.chains[this.curChain] != null) {
                this.curIterator = this.chains[this.curChain].iterator();
            }

        }

        @Override
        public boolean hasNext() {
            for (int i = this.curChain; i < chains.length; i++) {
                if (this.curIterator != null) {
                    if (this.curIterator.hasNext()) {
                        return true;
                    }
                }
                if (this.curChain == chains.length - 1) {
                    return false;
                }
                this.curChain++;
                if (chains[this.curChain] != null) {
                    curIterator = chains[this.curChain].iterator();
                } else {
                    this.curIterator = null;
                }
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (this.hasNext()) {
                return this.curIterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }

    }
}
