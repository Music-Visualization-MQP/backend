/*
package collageify.collageify.controller.spotify;

import java.util.concurrent.BlockingQueue;

public class DelayedSpotifyCredentialRefreshQueueProducer implements Runnable {
    private BlockingQueue<DelayedSpotifyCredentialRefreshObject> queue;
    private Integer numberOfElementsToProduce;
    private Integer delayOfEachProducedMessageMilliseconds;

    // standard constructor
    DelayedSpotifyCredentialRefreshQueueProducer(BlockingQueue<DelayedSpotifyCredentialRefreshObject> q){
        this.queue = q;
        numberOfElementsToProduce = q.size();
    }


    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayedSpotifyCredentialRefreshObject credentialRefreshObject
                    = new DelayedSpotifyCredentialRefreshObject();
            System.out.println("Put object: " + credentialRefreshObject);
            try {
                queue.put(credentialRefreshObject);
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
*/
