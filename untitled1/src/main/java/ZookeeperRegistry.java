public class ZookeeperRegistry implements IRegistry {

    @Override
    public void register(String url) {

        System.out.println("Register " + url + " service to Zookeeper");
    }
}