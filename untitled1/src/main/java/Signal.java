import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class Signal {
    private static volatile int signal = 0;

    public int print(List<String> list){
        //你是如何保证线程安全的呢？ 通过修改时加锁，并且修改时通过复制出一个新数组，在新数组上修改，修改完成之后再重新赋值回去
        //并且对于内部Object数组，是用volatile来修饰，来保证每一次修改完成之后，所有人拿到的都是最新的Object数组
        //所以你存在的问题时，读操作会读到旧数据。所以不能用于实时读的场景
        List<Integer> list1 = new CopyOnWriteArrayList<>();
        //至于你的你是使用双向链表来实现的，而且你的增删效率很高，是因为你不需要复制操作，只需要找到更新的位置，但是你的查找效率低。
        List<Integer> list2 = new LinkedList<>();
        //你的问题首先是一个扩容问题，你每次扩容的时候，变为原来的1.5倍，当然要在int的范围内。忘了说你的内部实现是一个数组。
        //还有一个要说明的问题时，你每次扩容时都会进行数组拷贝，而拷贝主要使用Arrays.copyOf和System.arraycopy两个函数
        //Arrays。copyOf底层就是调用的System.arraycopy
        //还有一个要说明的问题就是在迭代器迭代的时候，别调用自己的add和reomve方法，会抛异常。
        //因为有一个modcount变量，在迭代器初始化的时候会把这个值赋值给迭代器内部一个属性，就是固定的。
        //而add和remove方法都会使得modcount++，所以导致迭代器内部和外部不相等，所以抛异常。
        List<Integer> list3 = new ArrayList<>();

        //ArrayDeque和ArrayList区别其实很简单，这两一个是链表，一个是队列。
        //链表是可以通过传入index，移除任意位置的元素，而队列只能从队头或者队尾移除
        Deque<Integer> deque = new ArrayDeque<>();

        //今天研究HashMap
        //首先保证Node[] 的大小为2的n次方，大于等于传进来的初始容量大小
        //一个put的过程为，首先判断是否初始化了数组，如果没有则初始化；已经初始化了则检查是否可以直接插入桶位
        //如果不行，则要在链表上查找插入，如果已经存在了key，则修改value则可以。这两个是同时进行的。
        //如果没有插入，则直接插入链表尾部，并判断是否需要变为红黑树，当链表长度等于8时，就要变为红黑树
        //插入完成之后，需要判断当前map的size是否已经达到阈值，需要进行扩容。
        //扩容的原理， 每次扩容完的newTab的长度是oldTab的2倍
        //对于原始空桶位，不做处理，对于原始只有一个节点而不是链表的桶位，直接hash&(newCap-1)计算新桶位赋值过去就行
        //对于链表，则要进行遍历，通过hash&oldCap分成两个链表，为0的在原位置，为1的在当前位置+oldCap
        //remoce就没什么可说的了，老操作了和add相反而已。就是找到那个node，可能在桶的第一个，可能在链表中间，可能在树上，找到移除就可以，
        //没找到就返回null
        //写一个1.7和1.8hashmap并发的问题吧。
        //1.7由于头插法的问题，会产生循环链表已经数据丢失的问题
        //1.8采用尾插法，不会产生循环链表，但是还是存在数据覆盖的问题。
        Map<String,String> map = new HashMap<>();
        //看下TreeMap吧，有人面试问道treemap。TreeMap底层实现是红黑树。这里请注意，没有hash，所以和数组没有关系，只有红黑树。
        //所以TreeMap的get和put效率与红黑树是一致的。而排序是通过Comparator来完成的。
        Map<String, String> map1 = new TreeMap<>();
        //首先就是一个put原理，首先要明白1.8相比于1.7，只是锁桶位，所以效率高。
        //检查是否初始化，没有初始化则初始化。不需要加锁，通过cas和sizectl来实现
        //初始化过了，判断是否桶位为空，然后用volatile得到可见性，用cas网桶位添加。
        //如果正在扩容，即桶位的value=MOVED，则去帮助扩容，扩容之后在添加。
        //然后就是正常的链表或者树添加，锁整个链表或者树。
        //添加完之后，计算map的size大小，size的计算比较麻烦，因为是多线程情况，需要判断是否需要扩容。
        //如何计算size，size通过cas增加，增加成功p事没有，增加失败，则创建一个数组，每一个线程在不同下表下cas增加。size就是size+数组的sum。
        //关于ConcurrentHashMap为什么不能存null，这是因为ConcurrentHashMap中获取key的情况下，如果返回的结果是null，
        // 那么不能判断是由于这个key本身不存在导致的null，或者说是value的值本身就是null这一歧义问题。
        Map<String,String> maps = new ConcurrentHashMap<>();

        int[] nums = new int[]{};
        Arrays.sort(nums);


        return 0;
    }
    static class ThreadA implements Runnable {
        @Override
        public void run() {
            while (signal < 5) {
                if (signal % 2 == 0) {
                    System.out.println("threadA: " + signal);
                    synchronized (this) {
                        signal++;

                    }
                }
            }
        }
    }




    static class ThreadB implements Runnable {
        @Override
        public void run() {
            while (signal < 5) {
                if (signal % 2 == 1) {
                    System.out.println("threadB: " + signal);
                    synchronized (this) {
                        signal = signal + 1;
                    }
                }
            }
        }
    }

    public static String minWindow(String s, String t) {
        Map<Character, Integer> ori = new HashMap<Character, Integer>();
        int sum = 0;
        for(int i=0; i<t.length(); i++){
            char c = t.charAt(i);
            if(!ori.containsKey(c)){
                sum++;
            }
            ori.put(c,ori.getOrDefault(c,0)+1);
        }


        int left = 0;
        int minLen = s.length();
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(ori.containsKey(c)){
                ori.put(c, ori.get(c)-1);
                if(ori.get(c) == 0){
                    sum--;
                }
            }

            while(sum == 0){
                minLen = Math.min(minLen, i-left+1);
                System.out.println(s.substring(left, left+minLen));
                char L = s.charAt(left);
                if(ori.containsKey(L)){
                    ori.put(L, ori.get(L)+1);
                    if(ori.get(L) == 1){
                        sum++;
                    }
                }
                left++;
            }
        }
        return s.substring(left, left+minLen);
    }

    public static void main(String[] args){
        Queue q = new ArrayDeque();
    }
}