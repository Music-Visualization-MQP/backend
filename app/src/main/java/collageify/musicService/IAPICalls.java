package collageify.musicService;
public interface IAPICalls {

    public String getAlbumArt();
    public Integer getCurrentPopularity();
    public void UpdateDB() throws Exception;

}
