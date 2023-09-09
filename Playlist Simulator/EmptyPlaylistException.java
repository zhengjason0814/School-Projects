// Jason Zheng 114907558 R30

/**
 * This is the custom exception created for when a method
 * calls for a song record inside a playlist,
 * but the playlist is empty
 */
public class EmptyPlaylistException extends Exception {
    public EmptyPlaylistException(String str) {
        super(str);
    }
}
