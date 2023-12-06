/**
 * This is the custom exception created for when a playlist is either
 * too full for more songs or the array of playlists is
 * too full for more playlists
 */
public class FullPlaylistException extends Exception {
    public FullPlaylistException(String str) {
        super(str);
    }
}
