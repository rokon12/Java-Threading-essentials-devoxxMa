package ca.bazlur;

public class ThreadLocalDemo {
    private static final ThreadLocal<String> PRINCIPAL = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        var threadLocalDemo = new ThreadLocalDemo();
        threadLocalDemo.setPrincipal();
        threadLocalDemo.print();

        var t = new Thread(threadLocalDemo::print);
        t.start();
        t.join();
    }

    public void setPrincipal() {
        PRINCIPAL.set("ADMIN");
        System.out.println(Thread.currentThread());
    }

    public void print() {
        var principal = PRINCIPAL.get();
        System.out.println("principal = " + principal);
        System.out.println(Thread.currentThread());

    }
}
