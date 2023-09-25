package collageify.collageify.entities;

import collageify.collageify.controller.SpotifyApiController;

public class SpotifyClientCredentialGathererStrategy implements SpotifyClientCredentialManagementStrategy {

    @Override
    public void handleCredentials(SpotifyClientCredentials credentials, SpotifyApiController spotify) {

    }

    @Override
    public String getStrategyName() {
        return "gatherer";
    }

}
