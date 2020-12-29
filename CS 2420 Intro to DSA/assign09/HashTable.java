package assign09;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a Hash Table
 *
 * @param <K>
 * @param <V>
 * @author Erin Parker && Kyle Kazemini && Robert Davidson
 */
public class HashTable<K, V> implements Map<K, V> {

    private ArrayList<MapEntry<K, V>> table;
    private ArrayList<Boolean> safeDelete;

    private int capacity;
    private int size;

    private final int capacity_initial = 499;

    private int collisions;

    public int getCollisions() {
        return collisions;
    }

    /**
     *
     */
    public HashTable() {
        capacity = capacity_initial;
        table = new ArrayList<MapEntry<K, V>>();
        safeDelete = new ArrayList<Boolean>();

        for (int i = 0; i < capacity; i++) {
            table.add(null);
            safeDelete.add(false);
        }
    }

    /* Code Inspired By:
     * https://www.geeksforgeeks.org/program-to-find-the-next-prime-number/
     */
    static boolean isPrime(int n) {
        // Corner cases
        if (n <= 1)
            return false;

        if (n <= 3)
            return true;

        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (int i = 5; i * i <= n; i = i + 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }

    /* Code Inspired By:
     * https://www.geeksforgeeks.org/program-to-find-the-next-prime-number/
     */
    static int nextPrime(int N) {
        // Base case
        if (N <= 1)
            return 2;

        int prime = N;
        boolean found = false;

        // Loop continuously until isPrime returns
        // true for a number greater than n
        while (!found) {
            prime++;

            if (isPrime(prime))
                found = true;
        }

        return prime;
    }

    /**
     * Re-Hashes Table
     *
     * @param table - Current Table
     * @return Re-Hashed table
     */
    private ArrayList<MapEntry<K, V>> reHash(ArrayList<MapEntry<K, V>> table) {

        capacity = nextPrime(capacity + capacity);

        ArrayList<MapEntry<K, V>> t = new ArrayList<>();

        for (int i = 0; i < capacity; ++i) {
            t.add(null);

            if (i >= safeDelete.size())
                safeDelete.add(false);
            else {
                safeDelete.set(i, false);
            }
        }

        for (int i = 0; i < table.size(); ++i) {
            if (table.get(i) != null && !safeDelete.get(i)) {
                MapEntry<K, V> entry = table.get(i);
                int hashCode = Math.abs(entry.getKey().hashCode() % capacity);
                t.set(hashCode, entry);
            }
        }

        return t;
    }

    /**
     * Removes all mappings from this map.
     * <p>
     * O(table length)
     */
    public void clear() {
        table = new ArrayList<MapEntry<K, V>>();
        safeDelete = new ArrayList<Boolean>();

        for (int i = 0; i < capacity; i++) {
            table.add(null);
            safeDelete.add(false);
        }

        size = 0;
        capacity = capacity_initial;
    }

    /**
     * Determines whether this map contains the specified key.
     * <p>
     * O(1)
     *
     * @param key
     * @return true if this map contains the key, false otherwise
     */
    public boolean containsKey(K key) {
        int pos = Math.abs(key.hashCode()) % capacity;
        if (table.get(pos) == null)
            return false;
        else if (table.get(pos).getKey().equals(key) && !safeDelete.get(pos))
            return true;
        else {
            int i = 0;
            for (int N = 1; pos + (N * N) < table.size(); ++N) {
                i = Math.abs(pos + (N * N)) % table.size();
                if (table.get(i) != null) {
                    if (table.get(i).getKey().equals(key) && !safeDelete.get(i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines whether this map contains the specified value.
     * <p>
     * O(table length)
     *
     * @param value
     * @return true if this map contains one or more keys to the specified value,
     * false otherwise
     */
    public boolean containsValue(V value) {
        for (int i = 0; i < table.size(); ++i) {
            if (table.get(i) != null) {
                if (table.get(i).getValue().equals(value) && !safeDelete.get(i))
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns a List view of the mappings contained in this map, where the ordering of
     * mapping in the list is insignificant.
     * <p>
     * O(table length)
     *
     * @return a List object containing all mapping (i.e., entries) in this map
     */
    public List<MapEntry<K, V>> entries() {
        ArrayList<MapEntry<K, V>> list = new ArrayList<MapEntry<K, V>>();

        int sd_size = safeDelete.size();
        for (int i = 0; i < capacity; i++)
            if (table.get(i) != null && !safeDelete.get(i))
                list.add(table.get(i));

        return list;
    }

    /**
     * Gets the value to which the specified key is mapped.
     * <p>
     * O(1)
     *
     * @param key
     * @return the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key
     */
    public V get(K key) {
        int pos = Math.abs(key.hashCode() % capacity);

        if (!this.containsKey(key))
            return null;

        if (table.get(pos).getKey() == key && table.get(pos) != null
                && safeDelete.get(pos) == false) {
            return table.get(pos).getValue();
        } else {
            int i = findPos(key, pos);
            return table.get(i).getValue();
        }
    }

    /**
     * Determines whether this map contains any mappings.
     * <p>
     * O(1)
     *
     * @return true if this map contains no mappings, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Associates the specified value with the specified key in this map.
     * (I.e., if the key already exists in this map, resets the value;
     * otherwise adds the specified key-value pair.)
     * <p>
     * O(1)
     *
     * @param key
     * @param value
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    public V put(K key, V value) {
        V original = null;
        MapEntry<K, V> entry = new MapEntry<K, V>(key, value);

        int hashCode = Math.abs(key.hashCode()) % capacity;

        if (this.containsKey(key)) {
            original = this.get(key);
            int i = findPos(key, hashCode);
            table.set(i, entry);

            if (size + 1 > ((capacity / 2))) {
                table = reHash(table);
            }

            return original;
        } else {
            ++size;
            if (table.get(hashCode) == null || safeDelete.get(hashCode)) {
                table.set(hashCode, entry);

                if (size + 1 > ((capacity / 2))) {
                    table = reHash(table);
                }

                return null;
            } else {
                int i = findEmptyPos(hashCode);
                table.set(i, entry);

                if (size + 1 > ((capacity / 2))) {
                    table = reHash(table);
                }

                return null;
            }
        }
    }

    /**
     * Assumed that HashTable contains key.
     *
     * @param key
     * @param pos
     * @return pos of key
     */
    private int findPos(K key, int pos) {
        if (table.get(pos).getKey().equals(key) && !safeDelete.get(pos)) {
            return pos;
        } else {
            for (int N = 1; true; ++N) {
                int i = Math.abs(pos + (N * N)) % table.size();

                if (table.get(i).getKey().equals(key) && !safeDelete.get(i)) {
                    return i;
                }
            }
        }
    }

    /**
     * Assumed that HashTable does not contain key.
     *
     * @param pos
     * @return pos of first empty position.
     */
    private int findEmptyPos(int pos) {
        for (int N = 1; true; ++N) {
            int i = Math.abs(pos + (N * N)) % table.size();

            if (table.get(i) == null || safeDelete.get(i)) {
                return i;
            }
            ++collisions;
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * <p>
     * O(1)
     *
     * @param key
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    public V remove(K key) {
        V original = null;
        int pos = Math.abs(key.hashCode()) % capacity;

        if (this.containsKey(key)) {
            int i = findPos(key, pos);

            safeDelete.set(i, true);
            original = table.get(i).getValue();
            size--;
        }

        return original;
    }

    /**
     * Determines the number of mappings in this map.
     * <p>
     * O(1)
     *
     * @return the number of mappings in this map
     */
    public int size() {
        return size;
    }

}