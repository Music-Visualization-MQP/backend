package collageify.collageify.entities;

public class SpotifyClientCredentialRefresherStrategy implements SpotifyClientCredentialManagementStrategy {

    /**
     * this class is partially implemented the idea is that when action is called the credential is updated
     *
     *
     * TODO: implement a spotify stuff
     *
     */
    @Override
    public void action() {
        synchronized (this){


        }

    }

    @Override
    public SpotifyClientCredentialManagementStrategy next() {
        if(this.isValid())

    }


}
