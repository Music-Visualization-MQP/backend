package collageify.collageify;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollageifyService {
    private ExecutorService executorService;
    public CollageifyService(){
        executorService = Executors.newFixedThreadPool(10);
    }

    public void executeTask(Runnable task) {
        executorService.submit(task);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
