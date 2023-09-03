package collageify.collageify.controller.spotify;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayedSpotifyCredentialRefreshQueueConsumer implements Runnable {
    private BlockingQueue<DelayedSpotifyCredentialRefreshObject> queue;
    private Integer numberOfElementsToTake;
    public AtomicInteger numberOfConsumedElements = new AtomicInteger();

    // standard constructors

    @Override
    public void run() {
        try {
            DelayedSpotifyCredentialRefreshObject credentialRefreshObject = queue.take();
            //credentialRefreshObject.
            numberOfConsumedElements.incrementAndGet();
            System.out.println("Consumer take: " + credentialRefreshObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}