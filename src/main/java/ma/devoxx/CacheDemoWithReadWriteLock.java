package ma.devoxx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemoWithReadWriteLock {
    public static void main(String[] args) {


    }


    static class Cache {
        private Map<Long, User> cache = new HashMap<>();

        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public User get(String key) {
            try {
                readWriteLock.readLock().lock();
                return cache.get(key);
            } finally {
                readWriteLock.readLock().unlock();
            }
        }

        public void put(Long key, User value) {
            try {
                readWriteLock.writeLock().lock();
                cache.put(key, value);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        }
    }

    record User(String name) {
    }
}
