package assignment.dictionary;

/*

 */

//
import java.util.*;
import java.util.LinkedList;
import java.io.*;
import java.util.Dictionary;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.BiFunction;

/**

 */
public class MyHashTable<K, V> {
    private class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private final int capacity; // Example capacity
    private Set<K> keySet;
    private Collection<V> values;

    public MyHashTable() {
        capacity = 256; // Example capacity
        table = new LinkedList[capacity];
        size = 0;
        keySet = new HashSet<>();
        values = new ArrayList<>();
    }

    public V put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        } else {
            for (Entry<K, V> entry : table[index]) {
                if (entry.key.equals(key)) {
                    V oldValue = entry.value;
                    entry.value = value;
                    return oldValue;
                }
            }
        }

        table[index].add(new Entry<>(key, value));
        size++;
        keySet.add(key);
        values.add(value);
        return null;
    }

    public V get(Object key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        return null;
    }

    public V remove(Object key) {
        int index = hash(key);
        if (table[index] != null) {
            Iterator<Entry<K, V>> iterator = table[index].iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.key.equals(key)) {
                    V value = entry.value;
                    iterator.remove();
                    size--;
                    keySet.remove(key);
                    values.remove(value);
                    return value;
                }
            }
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return keySet.contains(key);
    }

    public Set<K> keySet() {
        return keySet;
    }

    public Collection<V> values() {
        return values;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
        size = 0;
        keySet.clear();
        values.clear();
    }

    private int hash(Object key) {
        return Math.abs(key.hashCode()) % capacity;
    }
}