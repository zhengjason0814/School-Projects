/**
 * This class represents song records inside a playlist,
 * where each song record has variables a song would have in real life.
 */
public class SongRecord implements Cloneable {
    private String title;
    private String artist;
    private int minutes;
    private int seconds;

    /**
     * Constructs a SongRecord object that will represent a song
     * in real life with similar attributes such as length, title, and artist.
     *
     * @param title this represents the title of the song
     * @param artist this represents the author of the song
     * @param minutes this represents how long the song is in minutes, if it's
     *                non-negative
     * @param seconds this represents how long the song is in seconds after
     *                considering minutes, between 0 and 59
     * @throws IllegalArgumentException exception is thrown if seconds
     *                                  or minutes violate their conditions
     */
    public SongRecord(String title, String artist, int minutes, int seconds) {
        this.title = title;
        this.artist = artist;
        if (minutes < 0) {
            throw new IllegalArgumentException
                    ("You cannot have negative minutes!");
        }
        this.minutes = minutes;
        if (seconds > 59 || seconds < 0) {
            throw new IllegalArgumentException
                    ("Please enter a valid seconds value! " +
                            "(Between 0 and 59)");
        }
        this.seconds = seconds;
    }

    /**
     * Returns the title of the song record
     *
     * @return the title of the song record
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the song
     *
     * @param title this represents the string the title will be modified to
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the artist of the song record
     *
     * @return the artist of the song record
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * Sets the artist of the song
     *
     * @param artist this represents the string the artist will be modified to
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Returns the minutes of the song record
     *
     * @return the minutes of the song record
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets how long the song is in minutes
     *
     * @param minutes the minutes of the song
     * @throws IllegalArgumentException if the value of minutes violates
     *                                  the non-negative condition
     */
    public void setMinutes(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException
                    ("You cannot have negative minutes!");
        }
        this.minutes = minutes;
    }

    /**
     * Returns the seconds of the song record
     *
     * @return the seconds of the song record
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Sets how long the song is in seconds after minutes have been
     * accounted for
     *
     * @param seconds the seconds of the song
     * @throws IllegalArgumentException if the value of seconds violates the
     *                                  condition of being between 0 and 59
     */
    public void setSeconds(int seconds) {
        if (seconds > 59 || seconds < 0) {
            throw new IllegalArgumentException
                    ("Please enter a valid seconds value!" +
                            " (Between 0 and 59)");
        }
        this.seconds = seconds;
    }

    /**
     * Returns a string representation of the song record
     *
     * @return the string representation of the song record
     */
    public String toString() {
        return this.title + " " + this.artist + " " +
                this.minutes + ":" + this.seconds;
    }
}
