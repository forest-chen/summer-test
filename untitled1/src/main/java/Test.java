import java.util.Iterator;
import java.util.*;
import java.util.ServiceLoader;

public class Test {
    //java的spi机制 即services provide inteface ,服务发现机制

    /**
     * 服务发现机制， 即java提供某些接口，例如jddbc， 由不同厂商，去具体的实现
     * 当需要使用某些实现时，由java内部寻找这些实现并实例化提供给调用者
     *
     * 原理：通过在META-INF/services 目录添加一个文件，文件名和接口全名称相同，
     * 所以文件完整路径是 META-INF/services/、、IRegistry
     * 文件内容为：
     * ZookeeperRegistry
     * EtcdRegistry
     *
     * 照我的理解和IOC的区别是，IOC是将创建的对象的所有权交给spring容器，但是你是知道需要创建那些对象的，并且和java内部实现的接口没有什么关系
     * spi则指的是对于调用某些java接口规定的和第三方工具交互接口实现的发现
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(Integer.MAX_VALUE);
    }
}