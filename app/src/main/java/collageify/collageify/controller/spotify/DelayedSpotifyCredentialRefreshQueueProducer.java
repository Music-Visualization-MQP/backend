/*

package collageify.collageify.controller.spotify;

import collageify.collageify.entities.SpotifyClientCredentials;

import java.util.concurrent.BlockingQueue;

public class DelayedSpotifyCredentialRefreshQueueProducer implements Runnable {
    private BlockingQueue<DelayedSpotifyCredentialRefreshObject> queue;
    private Integer numberOfElementsToProduce;
    private Integer delayOfEachProducedMessageMilliseconds;

    */
/**
     * TODO: Consider design more, this might do better taking in a strategy.
     *
     * passing a strategy in the constructor in might be advantageous because...
     * the data could come froo
     *
     * @param q represents a BlockingQueue representing Spotify refresh object
     *//*

    // standard constructor
    DelayedSpotifyCredentialRefreshQueueProducer(BlockingQueue<DelayedSpotifyCredentialRefreshObject> q){
        this.queue = q;
        numberOfElementsToProduce = q.size();
        run();
    }


    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            DelayedSpotifyCredentialRefreshObject credentialRefreshObject
                    = new DelayedSpotifyCredentialRefreshObject(new SpotifyClientCredentials(System));
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
