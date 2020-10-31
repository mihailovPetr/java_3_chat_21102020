import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static char currChar = 'A';

    public static void main(String[] args) {
        final Object mon = new Object();
        final int num = 5;

        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(() -> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (mon) {
                        while (currChar != 'A') {
                            mon.wait();
                        }
                        System.out.println("A");
                        currChar = 'B';
                        mon.notifyAll();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (mon) {
                        while (currChar != 'B') {
                            mon.wait();
                        }
                        System.out.println("B");
                        currChar = 'C';
                        mon.notifyAll();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (mon) {
                        while (currChar != 'C') {
                            mon.wait();
                        }
                        System.out.println("C");
                        currChar = 'A';
                        mon.notifyAll();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown();
    }
}
