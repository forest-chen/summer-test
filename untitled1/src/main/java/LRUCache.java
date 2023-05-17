import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    Map<Integer, Node> map;
    Node head;
    Node tail;
    int size;
    int capacity;
    class Node{
        int key;
        int value;
        Node next;
        Node prev;
        Node(){

        }
        Node(int key, int value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    public LRUCache(int capacity) {
        map = new HashMap<>(capacity);
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        size = 0;
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node != null){
            moveToHead(node);
            return node.value;
        }
        return -1;

    }
    
    public void put(int key, int value) {
        //已经满了
        if(size == capacity){
            Node node = tail.prev;
            deleteNode(node);
            System.out.println(node.value);
            map.remove(node.key);
            size--;
        }
        //如果当前包含要加入的key
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.value = value;
            moveToHead(node);
        }
        else{
            Node node = new Node(key, value, null);
            map.put(key, node);
            addToHead(node);
        }
        size++;
    }

    public void moveToHead(Node node){
        deleteNode(node);
        addToHead(node);
    }

    public void addToHead(Node node){
        node.next = head.next;
        node.next.prev = node; 
        head.next = node;
        node.prev = head;
    }

    public void deleteNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1,0);
        lruCache.put(2,2);
        System.out.println(lruCache.get(1));
        lruCache.put(3,3);
        System.out.println(lruCache.get(2));
        lruCache.put(4,4);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */