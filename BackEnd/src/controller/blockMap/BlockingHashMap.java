package controller.blockMap;

import java.util.HashMap;
import java.util.concurrent.*;

public class BlockingHashMap<K,V>{

    BlockingQueue<ConcurrentHashMap<K,V>> queueMap;
    ConcurrentHashMap<K,V> hashMap;
    public BlockingHashMap() {
        this.queueMap = new LinkedBlockingDeque<>();
        this.hashMap = new ConcurrentHashMap<>();
        try {
            this.queueMap.put(hashMap);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public V put(K key,V val){

        return null;
    }
}
