// Jason Zheng 114907558 R30

/**
 * This class represents a playlist and what can be done with it,
 * and the song records inside the playlist
 */
public class Playlist implements Cloneable {
    int MAXIMUM_SONGS = 50;
    SongRecord[] playlist;
    int songCount;
    String name;

    /**
     * Constructs a new empty playlist with no name
     */
    public Playlist() {
        playlist = new SongRecord[MAXIMUM_SONGS];
        songCount= 0;
    }
    /**
     * Constructs a new playlist with a name
     *
     * @param str the name of the playlist
     */
    public Playlist(String str) {
        playlist = new SongRecord[MAXIMUM_SONGS];
        songCount= 0;
        this.name = str;
    }

    /**
     * Gets the name of the playlist
     *
     * @return the name of the playlist
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the playlist
     *
     * @param name the name of the playlist
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method clones a specified playlist and then returns a
     * new playlist which was a copy of the original
     *
     * @return the copy of the original playlist
     * @throws CloneNotSupportedException if cloning is not supported,
     * throw exception
     */
    public Object clone() throws CloneNotSupportedException {
        Playlist clone;
        try {
            clone = (Playlist) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneNotSupportedException("Clone is not supported!");
        }
        clone.playlist = (SongRecord[]) playlist.clone();
        return clone;
    }

    /**
     * This is a method that compares two objects together in order to
     * figure out whether both objects are equal to each other.
     * In this case, we are typically comparing two playlists together
     * to see if they have the same song records in the same order without
     * comparing the names of the two playlists. If they are,
     * return boolean value true, if not, false.
     *
     * @param obj the object to be compared to
     * @return true if the object that is being compared to is equal to
     * itself, if not false
     */
    public boolean equals (Object obj) {
        if (obj instanceof Playlist) {
            if (((Playlist) obj).songCount == songCount ) {
                for (int i = 0; i < songCount - 1; i++) {
                    if (((Playlist) obj).playlist[i].
                            getTitle().compareToIgnoreCase
                                    (playlist[i].getTitle()) != 0) {
                        return false;
                    }
                    if (((Playlist) obj).playlist[i].
                            getArtist().compareToIgnoreCase
                                    (playlist[i].getArtist()) != 0) {
                        return false;
                    }
                    if (((Playlist) obj).playlist[i].
                            getMinutes() != playlist[i].
                            getMinutes()) {
                        return false;
                    }
                    if (((Playlist) obj).playlist[i].
                            getSeconds() != playlist[i].
                            getSeconds()) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Returns the size of the playlist according to the amount
     * of songs it has
     *
     * @return the amount of songs the playlist currently has
     */
    public int size() {
        return songCount;
    }

    /**
     * Creates and adds a new song according to user input at a
     * specified location. If position is taken, move all existing
     * songs above and at the position an index higher to leave
     * space for the new song added
     *
     * @param title the title of the new song
     * @param artist the artist of the new song
     * @param minutes how long the song is in minutes
     * @param seconds how long the song is in seconds
     * @param position the position where the song is to be added
     *                 to the playlist
     * @throws IllegalArgumentException is thrown if an invalid
     *                                  position is given
     * @throws FullPlaylistException is thrown if the current
     *                               playlist is thrown
     */
    public void addSong(String title, String artist, int minutes, int seconds,
                        int position)
            throws FullPlaylistException, IllegalArgumentException {
        if (position > 50) {
            throw new IllegalArgumentException(position + " is not " +
                    "a valid position for this song! (1 through 50)");
        } else if (songCount == 50) {
            throw new FullPlaylistException("This playlist is full!");
        } else if (position > songCount + 1) {
            throw new IllegalArgumentException("This is not a good " +
                    "position for this song record! " +
                    "(Let's not have empty spaces in our playlist!)");
        }
        SongRecord newSong = new SongRecord(title, artist, minutes, seconds);
        if (playlist[position - 1] != null) {
            for (int i = songCount - 1; i >= position - 1; i--) {
                playlist[i + 1] = playlist[i];
            }
        }
        playlist[position - 1] = newSong;
        songCount++;
    }

    /**
     * Removes a song at a specified position on the playlist,
     * repositions songs if a variable is removed in the middle of the
     * playlist
     *
     * @param position the position of the song that the user wishes to remove
     * @throws IllegalArgumentException if the given position was
     *                                  an invalid position
     * @throws EmptyPlaylistException if the playlist was empty,
     *                                throw this exception
     */
    public void removeSong(int position)
            throws EmptyPlaylistException {
        if (position > 50) {
            throw new IllegalArgumentException(position +
                    " is not a valid position. (1 through 50)");
        } else if (position > songCount) {
            throw new IllegalArgumentException
                    ("There are no song records in this position.");
        } else if (songCount == 0) {
            throw new EmptyPlaylistException
                    ("There are no songs to remove!");
        }
        if (playlist[position] != null) {
            for (int i = position; i < songCount - 1; i++) {
                playlist[i - 1] = playlist[i];
            }
        } else {
            playlist[position - 1] = playlist[position];
        }
        songCount--;
    }

    /**
     * Returns a song record and its information
     * @param position the position of the song the user wishes to get/return
     * @return the song record the user has requested
     * @throws IllegalArgumentException is thrown if an invalid position was
     *                                  given to return
     * @throws EmptyPlaylistException is thrown if the playlist is empty
     */
    public SongRecord getSong(int position) throws EmptyPlaylistException {
        if (position > 50) {
            throw new IllegalArgumentException(position +
                    " is not a valid position. (1 through 50)");
        } else if (position > songCount + 1) {
            throw new IllegalArgumentException
                    ("There are no song records in this position.");
        } else if (songCount == 0) {
            throw new EmptyPlaylistException
                    ("There are no songs in this playlist!");
        }
        SongRecord answer = playlist[position - 1];
        return answer;
    }

    /**
     * Prints all the songs inside the playlist in a table format,
     * the position the is in, title, artist, and its length
     *
     * @throws EmptyPlaylistException if the playlist is empty
     */
    public void printAllSongs() throws EmptyPlaylistException {
        if (songCount == 0) {
            throw new EmptyPlaylistException
                    ("There are no songs in this playlist to print!");
        }
        System.out.printf
                ("%-10s%-15s%-25s%-8s\n", "Song#","Title", "Artist", "Length");
        System.out.println();
        for (int i = 0; i < songCount; i++) {
            if (playlist[i] != null) {
                String customLength = String.format
                        ("%02d:%02d",playlist[i].getMinutes(),
                                playlist[i].getSeconds());
                System.out.printf
                        ("%-10d%-15s%-25s%-8s\n", i + 1 ,
                                playlist[i].getTitle(),
                                playlist[i].getArtist(),
                                customLength);
            } else {
                continue;
            }
        }
    }

    /**
     * Returns a playlist that only has songs of a certain user-given artist
     *
     * @param originalList the original list the new playlist has taken from
     * @param artist the artist that we are taking all the songs from
     *               the original playlist
     * @return the new playlist that has been created with only
     *         the artists songs
     * @throws FullPlaylistException is thrown if there is no space left
     *                               to create a new artist-specified playlist
     */
    public static Playlist getSongsByArtist
    (Playlist originalList, String artist) throws FullPlaylistException {
        Playlist artistPlaylist = new Playlist();
        for (int i = originalList.songCount - 1; i >= 0; i--) {
            if (originalList.playlist[i].getArtist().
                    compareToIgnoreCase(artist) == 0) {
                artistPlaylist.addSong(originalList.playlist[i].getTitle()
                        ,originalList.playlist[i].getArtist(),
                        originalList.playlist[i].getMinutes(),
                        originalList.playlist[i].getSeconds(),
                        1);
            }
        }
        return artistPlaylist;
    }

    /**
     * Returns null since there was a better way to represent the playlist
     * string representation in a different method
     *
     * @return null since it is not being used
     */
    public String toString() {
        return null;
    }
}
