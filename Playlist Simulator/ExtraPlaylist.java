/**
 * This class represents the array of
 * playlists created in order to handle multiple playlists
 */
public class ExtraPlaylist {
    int MAXIMUM_PLAYLISTS = 50;
    Playlist[] playlistArray;
    int playlistCount;

    /**
     * Returns how many playlists in total there are
     *
     * @return the number of playlists inside the playlist array
     */
    public int getPlaylistCount() {
        return playlistCount;
    }

    /**
     * Sets the playlist counter to a certain value
     *
     * @param playlistCount the new number of songs assigned to the
     *                      number of playlists inside the array
     */

    public void setPlaylistCount(int playlistCount) {
        this.playlistCount = playlistCount;
    }

    /**
     * Constructs a new "extra" playlist object representing
     * the array of playlists
     */
    public ExtraPlaylist() {
        playlistArray = new Playlist[MAXIMUM_PLAYLISTS];
        playlistCount = 0;
    }

    /**
     * Adds an empty playlist to the playlist array
     *
     * @param name the name of the newly created playlist
     * @throws FullPlaylistException is thrown if the array of
     *                               playlists is full
     */
    public void addPlaylist(String name) throws FullPlaylistException {
        if (playlistCount > 50) {
            throw new FullPlaylistException("There are too many playlists!");
        }
        Playlist addedPlaylist = new Playlist(name);
        addedPlaylist.setName(name);
        playlistArray[playlistCount] = addedPlaylist;
        playlistCount++;
    }

    /**
     * Returns the index of the target playlist inside the array of playlists
     *
     * @param target the name of the playlist the method is attempting
     *               to find the position of
     * @return the index of the target playlist
     */

    public int findPlaylistPosition(String target) {
        for (int i = 0; i <= playlistCount - 1; i++) {
            if (playlistArray[i].getName().compareToIgnoreCase(target)
                    == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Displays all the existing playlist's names
     */
    public void displayPlaylists() {
        System.out.println("All Playlists : ");
        for(int i = 0; i < playlistCount; i++) {
            System.out.println(playlistArray[i].getName());
        }
    }

    /**
     * Adds a new playlist created most likely from another method such as
     * the artist playlist method
     *
     * @param addedPlaylist the playlist that is going to be added to the
     *                      array of playlists
     * @param name the name of the playlist that is going to be added
     * @throws FullPlaylistException is thrown if there is no space inside
     *                      the array of playlists
     */
    public void addExistingPlaylist(Playlist addedPlaylist, String name)
            throws FullPlaylistException {
        if(playlistCount > 50) {
            throw new FullPlaylistException("There are too many " +
                    "playlists for a new one!");
        }
        addedPlaylist.setName(name);
        playlistArray[playlistCount] = addedPlaylist;
        playlistCount++;
    }
}
