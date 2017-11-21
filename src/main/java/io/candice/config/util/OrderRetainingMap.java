package io.candice.config.util;

import java.util.*;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-18
 */
public class OrderRetainingMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = -7287496822993252974L;

    private Set<K> keyOrder = new ArraySet<K>();
    private List<V> valueOrder = new ArrayList<V>();

    @Override
    public V put(K key, V value) {
        keyOrder.add(key);
        valueOrder.add(value);
        return super.put(key, value);
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableList(valueOrder);
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(keyOrder);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    private static class ArraySet<T> extends ArrayList<T> implements Set<T> {

        private static final long serialVersionUID = 1L;
    }
}
