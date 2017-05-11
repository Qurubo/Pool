import java.util.Random;

/**
 * Created by qurub on 01.05.2017.
 */
public class Run {
    public static void main(String[] args) {
        WorkQueue workQueue = new WorkQueue(4);
        for (int i = 0; i < 290; i++) {
            workQueue.execute(new Worker(i));

        }
    }
}
