package collageify.collageify.controller.spotify;

import collageify.collageify.entities.SpotifyClientCredentials;
import com.google.common.primitives.Ints;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedSpotifyCredentialRefreshObject implements Delayed {
    private SpotifyClientCredentials credentials;
    private long startTime;

    public DelayedSpotifyCredentialRefreshObject(SpotifyClientCredentials credentials) {
        this.credentials = credentials;
        this.startTime = credentials.getAccessTokenExpTime().toInstant().toEpochMilli() - 5 * 60 * 1000;
    }
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
    @Override
    public int compareTo(Delayed o) {
        return Ints.saturatedCast(
                this.startTime - ((DelayedSpotifyCredentialRefreshObject) o).startTime);
    }
    public String getRefreshToken(){
        return this.credentials.getRefreshToken();
    }

}
