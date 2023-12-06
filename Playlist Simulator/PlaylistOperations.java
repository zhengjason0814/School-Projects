import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class is the main driver class that implements all the operations
 * done on a playlist program
 */
public class PlaylistOperations {
    private static Playlist playlist = new Playlist("Default");
    private static ExtraPlaylist playArray = new ExtraPlaylist();

    /**
     * The main method which includes the do while loop that handles all the
     * user inputs for the user-interface interaction with the playlists
     * and its records.
     *
     * It has 12 options :
     * ADD SONG - Adds a song to a playlist
     * GET SONG - Grabs information about a user-specified song in a playlist
     * REMOVE SONG - Removes a user-specified song from a playlist
     * PRINT ALL SONGS - Prints all songs inside a playlist
     * PRINT SONGS BY ARTIST - Creates a new playlist customized with only a
     * user-specified artist's songs
     * SIZE  - Shows how many songs are in the current playlist
     * NEW PLAYLIST - Creates a new empty playlist and adds it to the array of
     * playlists, then switches to that playlist
     * CHANGE CURRENT PLAYLIST - Changes the current playlist to another one
     * that the user wishes to switch to
     * COPY PLAYLIST TO ANOTHER - Copies the current playlist and adds the
     * newly copied playlist to the array of playlists with a user-specified
     * name
     * COMPARE - Compares the current playlist with a user-specified playlist
     * and checks if they are equal to each other
     * (Besides the names of the playlist)
     * DISPLAY ALL PLAYLISTS - Displays all the names of the current playlists
     * inside of the array of playlists
     *
     * @param args the arguments within the main method
     * @throws FullPlaylistException is thrown when a playlist is either too
     * full for more songs or
     * the array of playlists is too full for more playlists
     * @throws EmptyPlaylistException is thrown when a method calls to an
     * empty playlist
     * @throws CloneNotSupportedException is thrown when cloning is not
     * supported when a method calls for it
     */
    public static void main (String [] args) throws FullPlaylistException,
            EmptyPlaylistException, CloneNotSupportedException {
        String title, artist, customLength, name;
        int minutes, seconds, position;
        Scanner input = new Scanner(System.in);
        int currentPlaylist = 0;
        String option;

        playArray.playlistArray[currentPlaylist] = playlist;
        playArray.setPlaylistCount(playArray.getPlaylistCount() + 1);

        do {
            System.out.println("Menu : Select an option");
            System.out.println();
            System.out.println("(A) - Add Song");
            System.out.println("(G) - Get Song");
            System.out.println("(R) - Remove Song");
            System.out.println("(P) - Print All Songs");
            System.out.println("(B) - Print Songs by Artist");
            System.out.println("(S) - Size");
            System.out.println("(N) - New Playlist");
            System.out.println("(V) - Change Current Playlist");
            System.out.println("(C) - Copy Playlist To Another");
            System.out.println("(E) - Compare");
            System.out.println("(D) - Display All Playlists");
            System.out.println("(Q) - Quit");

            option = input.nextLine();
            switch (option) {
                case "A":
                    try {
                        System.out.println("What is the title?");
                        title = input.nextLine();
                        System.out.println("Who was the artist?");
                        artist = input.nextLine();
                        System.out.println("How many minutes?");
                        minutes = input.nextInt();
                        input.nextLine();
                        System.out.println("How many seconds?");
                        seconds = input.nextInt();
                        input.nextLine();
                        System.out.println("What position " +
                                "in this playlist do you want to place " +
                                "this song?");
                        position = input.nextInt();
                        input.nextLine();
                        playArray.playlistArray[currentPlaylist].
                                addSong(title, artist, minutes, seconds,
                                        position);
                        System.out.println(title + " by " + artist + " was " +
                                "added to the playlist in position "
                                + position + ".");
                        System.out.println("Currently on playlist " +
                                playArray.playlistArray
                                        [currentPlaylist].getName());
                    } catch (InputMismatchException e) {
                        System.out.println("ERROR! Don't put letters when" +
                                " it asks for time!");
                        input.nextLine();
                    } catch (FullPlaylistException e) {
                        System.out.println("Playlist is full! Cannot " +
                                "add more!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("This is not a valid position" +
                                " for your song!");
                    }
                    System.out.println();
                    break;
                case "G":
                    try {
                        System.out.println("What is the position you " +
                                "would like to get?");
                        position = input.nextInt();
                        input.nextLine();
                        customLength = String.format("%02d:%02d",
                                playArray.playlistArray[currentPlaylist].
                                        getSong(position).getMinutes(),
                                playArray.playlistArray[currentPlaylist].
                                        getSong(position).getSeconds());
                        System.out.println
                                (playArray.playlistArray[currentPlaylist].
                                getSong(position).getTitle() + " by " +
                                playArray.playlistArray[currentPlaylist].
                                        getSong(position).getArtist() +
                                "    " + customLength);
                        System.out.println("Successfully retrieved song " +
                                "at position " + position + "!");
                        System.out.println("Currently on playlist " +
                                playArray.playlistArray[currentPlaylist].
                                        getName());
                    } catch (InputMismatchException e) {
                        System.out.println("Careful! Only integers " +
                                "are allowed for the request for position!");
                        input.nextLine();
                    }
                    System.out.println();
                    break;
                case "R":
                    try {
                        System.out.println("What is the position of " +
                                "the song you want to remove?");
                        position = input.nextInt();
                        input.nextLine();
                        playArray.playlistArray
                                [currentPlaylist].removeSong(position);
                        System.out.println("Successfully deleted song at " +
                                "position " + position);
                        System.out.println("Currently on playlist " +
                                playArray.playlistArray[currentPlaylist].
                                        getName());
                    } catch (InputMismatchException e) {
                        System.out.println("Careful! Only integers are " +
                                "allowed for the request for position!");
                        input.nextLine();
                    } catch (IllegalArgumentException e) {
                        System.out.println("That position is not a " +
                                "valid position for removal!");
                    } catch (EmptyPlaylistException e) {
                        System.out.println("There are no songs inside " +
                                "of the playlist to remove!");
                    }
                    System.out.println();
                    break;
                case "P":
                    try {
                        playArray.playlistArray[currentPlaylist].
                                printAllSongs();
                        System.out.println();
                        System.out.println("Successfully printed all " +
                                "songs inside of this playlist!");
                        System.out.println("Currently on playlist " +
                                playArray.playlistArray[currentPlaylist].
                                        getName());
                    } catch (EmptyPlaylistException e) {
                        System.out.println("The playlist is empty! " +
                                "Cannot print an empty playlist.");
                    }
                    System.out.println();
                    break;
                case "B":
                    try {
                        System.out.println("What is the artist?");
                        artist = input.nextLine();
                        Playlist artistPlaylist = playArray.playlistArray
                                [currentPlaylist].getSongsByArtist
                                (playArray.playlistArray
                                        [currentPlaylist], artist);
                        playArray.addExistingPlaylist(artistPlaylist, artist);
                        System.out.printf("%-10s%-15s%-25s%-8s\n", "Song#",
                                "Title", "Artist", "Length");
                        for (int i = 0; i < artistPlaylist.songCount; i++) {
                            SongRecord currentSong = artistPlaylist.getSong
                                    (i + 1);
                            customLength = String.format("%02d:%02d",
                                    currentSong.getMinutes(),
                                    currentSong.getSeconds());
                            System.out.printf("%-10d%-15s%-25s%-8s\n",
                                    i + 1,
                                    currentSong.getTitle(),
                                    currentSong.getArtist(),
                                    customLength);
                        }
                        System.out.println("Successfully temporarily made " +
                                "a list specified for the artist : " +
                                artist);
                        System.out.println("Currently on playlist " +
                                playArray.playlistArray[currentPlaylist].
                                        getName());
                    } catch (FullPlaylistException e) {
                        System.out.println("The playlist is too full, " +
                                "cannot add another playlist!");
                    }
                    System.out.println();
                    break;
                case "S":
                    System.out.println("There are " +
                            playArray.playlistArray[currentPlaylist].
                                    songCount +
                            " song(s) in the current playlist");
                    System.out.println("Currently on playlist " +
                            playArray.playlistArray[currentPlaylist].
                                    getName());
                    System.out.println();
                    break;
                case "N":
                    try {
                        System.out.println("What name would you like to" +
                                " give to this new playlist?");
                        name = input.nextLine();
                        playArray.addPlaylist(name);
                        currentPlaylist =
                                playArray.findPlaylistPosition(name);
                        System.out.println
                                ("Successfully created a new playlist named "
                                        + name + "!");
                        System.out.println
                                ("Currently on playlist " +
                                        playArray.playlistArray
                                        [currentPlaylist].getName());
                    } catch (FullPlaylistException e) {
                        System.out.println("There are too many playlists," +
                                " no more playlists can be made.");
                    }
                    System.out.println();
                    break;
                case "V":
                    System.out.println("What is the name of the " +
                            "playlist you wish to switch to?");
                    name = input.nextLine();
                    currentPlaylist = playArray.findPlaylistPosition(name);
                    System.out.println("Successfully switched to playlist"
                            + name + "!");
                    System.out.println("Currently on playlist " +
                            playArray.playlistArray
                                    [currentPlaylist].getName());
                    System.out.println();
                    break;
                case "C":
                    try {
                        System.out.println("You are trying to copy " +
                                "the current playlist into a new playlist. " +
                                "What would you like " +
                                "to name this new playlist?");
                        name = input.nextLine();
                        playArray.addExistingPlaylist((Playlist)
                                playArray.playlistArray
                                        [currentPlaylist].clone(), name);
                        System.out.println("Added a copy of " +
                                playArray.playlistArray[currentPlaylist].
                                        getName()
                                + " named " + name + "!");
                    } catch (CloneNotSupportedException e) {
                        System.out.println("Cannot be cloned! " +
                                "The clone is not supported unfortunately.");
                    } catch (FullPlaylistException e) {
                        System.out.println("There are too many playlists," +
                                " no more playlists can be made.");
                    }
                    System.out.println();
                    break;
                case "E":
                    System.out.println("You are trying to compare the " +
                            "current playlist with another, what is " +
                            "the name of the other playlist?");
                    name = input.nextLine();
                     if (playArray.playlistArray[currentPlaylist].equals
                             (playArray.playlistArray[playArray.
                                     findPlaylistPosition(name)])) {
                         System.out.println("The two playlists are " +
                                 "the same! (Besides the name of the " +
                                 "playlist)");
                     } else {
                         System.out.println("The two playlists are " +
                                 "not the same.");
                     }
                    System.out.println("Currently on playlist " +
                            playArray.playlistArray[currentPlaylist].
                                    getName());
                    System.out.println();
                    break;
                case "D":
                    System.out.println("Displaying all playlists...");
                    playArray.displayPlaylists();
                    System.out.println("Successfully displayed" +
                            " all playlists!");
                    break;
                default:
                    System.out.println("That is not a valid " +
                            "option! Try again.");
                    break;
            }
        } while (!(option.compareTo("Q") == 0));
    }
}
