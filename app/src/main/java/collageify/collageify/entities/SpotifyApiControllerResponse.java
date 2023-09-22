package collageify.collageify.entities;

public class SpotifyApiControllerResponse<T> {
    private final T data;

    private SpotifyApiControllerResponse(T data){
        this.data = data;
    }

    public T getData(){
        return this.data;
    }

    public static <T> SpotifyApiControllerResponse<T> withResponse(T data){
        return new SpotifyApiControllerResponse<>(data);
    }
    public static <T> SpotifyApiControllerResponse<T> empty(){
        return new SpotifyApiControllerResponse<T>(null);
    }

    public boolean hasData() {
        return data != null;
    }
}