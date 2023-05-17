public class EtcdRegistry implements IRegistry {

    @Override
    public void register(String url) {

        System.out.println("Register " + url + " service to Etcd");
    }
}