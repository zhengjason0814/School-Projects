/**
 * This is the custom exception creation for when a playlist is not found
 */
public class PlaylistNotFoundException extends Exception {
    public PlaylistNotFoundException(String str) {
        super(str);
    }
}
