package com.tmri.pub.util;
import java.util.ArrayList;   
import java.util.Collection;   
import java.util.LinkedHashMap;   
import java.util.Map;   
import java.util.concurrent.locks.Lock;   
import java.util.concurrent.locks.ReadWriteLock;   
import java.util.concurrent.locks.ReentrantReadWriteLock; 
  
  
/** 
 * 类说明：利用LinkedHashMap实现LRU,缓存容量在生成实例时指定,默认为1000
 * 
 *  
 * @modifier chiva 
 * @date 2010-07-02
 * @param <K> 
 * @param <V> 
 */  

  
  
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V>{   
    private static final long serialVersionUID = -6970580442519842034L;   
    private final int maxCapacity;   
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;   
    //private final Lock lock = new ReentrantLock();   
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();   
    private final Lock readLock = rwlock.readLock();   
    private final Lock writeLock = rwlock.writeLock();   
  
    public LRULinkedHashMap(int maxCapacity) {   
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);   
        this.maxCapacity = maxCapacity;   
    }   
    public LRULinkedHashMap() {   
        super(1000, DEFAULT_LOAD_FACTOR, true);   
        this.maxCapacity = 1000;   
    }   
    @Override  
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {   
        return size() > maxCapacity;   
    }   
  
    @Override  
    public boolean containsKey(Object key) {   
        try {   
            readLock.lock();   
            return super.containsKey(key);   
        } finally {   
            readLock.unlock();   
        }   
    }   
  
    @Override  
    public V get(Object key) {   
        try {   
            readLock.lock();   
            return super.get(key);   
        } finally {   
            readLock.unlock();   
        }   
    }   
  
    @Override  
    public V put(K key, V value) {   
        try {   
            writeLock.lock();   
            return super.put(key, value);   
        } finally {   
            writeLock.unlock();   
        }   
    }   
  
    public int size() {   
        try {   
            readLock.lock();   
            return super.size();   
        } finally {   
            readLock.unlock();   
        }   
    }   
  
    public void clear() {   
        try {   
            writeLock.lock();   
            super.clear();   
        } finally {   
            writeLock.unlock();   
        }   
    }   
  
    public Collection<Map.Entry<K, V>> getAll() {   
        try {   
            readLock.lock();   
            return new ArrayList<Map.Entry<K, V>>(super.entrySet());   
        } finally {   
            readLock.unlock();   
        }   
    }   
}  