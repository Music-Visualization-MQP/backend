package collageify.collageify.entities;

public interface SpotifyClientCredentialManagementStrategy<T extends SpotifyClientCredentials> {


    /**
     * Side Effect Only: This method will do something with the credentials, whether it is to update them, or
     * to gather data, it is up to you!
     *
     * TODO: implement this action method. one should get a new token, the other should be used to fetch data and send it to a filter class and on to the database
     */
    public void action();

    public SpotifyClientCredentialManagementStrategy next() ;
}
