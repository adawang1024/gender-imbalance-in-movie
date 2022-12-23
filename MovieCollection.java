import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;
import javafoundations.*;

/**
 * Represents a collection of Movies. Uses a LinkedList to hold the movie 
 * objects. The complete Movie data (title and test results) are coming from 
 * a file named "nextBechdel_allTests.txt". Data regarding actors who 
 * participated in each movie is read from a file named "nextBechdel_castGender.txt". 
 * All files are located in the "data" folder.
 *
 * @author Genevieve Mellott, Ada Wang, Anna Zhou (based in part on javadoc by CS230 Instructors)
 * @version 12/16/2022
 */
public class MovieCollection {
    private LinkedList<Movie> allMovies;
    private LinkedList<Actor> allActors;
    private String testsFileName;
    private String castsFileName;
    //assumes that all movie titles, and no more, included in the testsFileName
    //are included in the castsFileName file

    /**
     * Constructor for objects of class MovieCollection
     * 
     * @param  testsFileName  The name of the file with test data (movie title 
     * and alternate Bechdel test results)
     * @param  castsFileName  The name of the file with data regarding actors 
     * who participated in each movie
     */
    public MovieCollection(String testsFileName, String castsFileName) {
        allMovies = new LinkedList<Movie>();
        allActors = new LinkedList<Actor>();
        this.testsFileName = testsFileName;
        this.castsFileName = castsFileName;
        // fills allMovies and allActors with data from files
        this.readMovies();
        this.readCasts();
    }

    /**
     * Returns the names of all actors in the collection
     * 
     * @return  a LinkedList with the names of all actors
     */
    public LinkedList<String> getActorNames() {
        LinkedList<String> names = new LinkedList<String>();
        for (Actor a : allActors){
            names.add(a.getName());
        }
        return names;
    }

    /**
     * Returns all the Actors in the collection 
     * 
     * @return  a LinkedList with all the Actors, each complete with their 
     * name and gender.
     */
    public LinkedList<Actor> getActors() {
        LinkedList<Actor> actors = new LinkedList<Actor>();
        for (Actor a : allActors){
            actors.add(a);
        }
        return actors;
    }

    /**
     * Returns all the movies in a LinkedList
     * 
     * @return  a LinkedList with all the movies, each complete with its 
     * title, actors and Bechdel test results.

     */
    public LinkedList<Movie> getMovies() {
        LinkedList<Movie> movies = new LinkedList<Movie>();
        for (Movie m : allMovies){
            movies.add(m);
        }
        return movies;
    }

    /**
     * Returns the titles of all movies in the collection
     * 
     * @return  a LinkedList with the titles of all the movies
     */
    public LinkedList<String> getMovieTitles() {
        LinkedList<String> titles = new LinkedList<String>();
        for (Movie m : allMovies){
            titles.add(m.getTitle());
        }
        return titles;
    }

    /**
     * This method reads from the file with the information on movie titles 
     * and the corresponding test results, (“nextBechdel_allTests.txt”), and
     * populates the relevant data structures. It uses the first column in
     * the data (the movie title) to create all movie objects, and adds the 
     * included information on the Bechdel-alternative test results to each 
     * movie.
     * 
     * Assumes that the data in the file is formatted correctly (every line,
     * after the first line in the file, contains the movie title, followed by 
     * the 13 individual test scores, separated by commas).
     * For example: "Bad Moms,0,0,0,1,0,0,0,1,0,0,1,1,1"
     * 
     */
    private void readMovies() {
        try {
            Scanner fileScan = new Scanner(new File(testsFileName));
            fileScan.nextLine(); //skipping first line

            while (fileScan.hasNext()) {
                fileScan.useDelimiter(",");
                Movie m = new Movie(fileScan.next()); //first element is the title

                String testResults = fileScan.nextLine().substring(1);
                //reading the rest of the line of the file, which are the test
                //results, and getting rid of extra comma at start of string

                m.setTestResults(testResults); //storing in Movie object
                m.feministScore();
                allMovies.add(m); //adding to collection
            }
            fileScan.close();
        } catch (IOException ex) {
            System.out.println("Error trying to use file named '" 
                + testsFileName + "'\n\t" + ex);
        }
    }

    /**
     * Reads the casts for each movie, from input casts file; Assumes lines in 
     * this file are formatted as follows: "MOVIE","ACTOR","CHARACTER_NAME",
     * "TYPE","BILLING","GENDER"
     * For example: "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * It reads from the “nextBechdel_castGender.txt” the information on the 
     * cast for each movie, and populates the appropriate instance variables.
     * 
     */
    private void readCasts() {
        try { // checking to see if you can use file
            Scanner fileScan = new Scanner(new File(castsFileName));
            
            for (Movie m : allMovies){
                m.addAllActors(castsFileName);
                for(Actor a : m.getAllActors().keySet()){
                    if(!allActors.contains(a))
                        allActors.add(a);
                }
            }
            fileScan.close();
        } catch (IOException ex) {
            System.out.println("Error trying to use file named '" 
                + castsFileName + "'\n\t" + ex);
        } 
    }

    /**
     * Returns a list of all Movies that pass the n-th feminism test.
     * 
     * The method takes as input an integer (in the range 0 to 12, inclusively) 
     * indicating the number of an alternate Bechdel test, and returns all 
     * the movies that passed that particular test. For example,
     * findAllMoviesPassedTestNum(2) will return all movies which passed 
     * the Peirce Test.
     * 
     * @param  n  integer identifying the n-th test in the list of 13 
     * gender imbalance tests, starting from zero
     * @return  A list of all Movies which have passed the n-th test
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum(int n) {
        LinkedList<Movie> passed = new LinkedList<Movie>();

        for (Movie m : allMovies){
            Vector<String> results = m.getAllTestResults();

            // checks if it passed the specific test
            if ((results.get(n)).equals("0")) { 
                passed.add(m);
            }
        }

        return passed;
    }

    /**
     * Finds all movies that passed the Bechdel test. Returns a string that 
     * includes the total number of movies that passed the Bechdel test, 
     * as well as their titles, one movie per line.
     * 
     * @return a String representation of the movies that passed the Bechdel
     * Test
     */
    public String bechdelPass() {
        LinkedList<Movie> passed = findAllMoviesPassedTestNum(0);
        String s = "";
        int size = passed.size();

        for (Movie m : passed) {
            s += "\n\t" + m.getTitle();
        }

        return s = size + " movie" + (size == 1 ? "" : "s") + " passed the "
            + "Bechdel test" + (size == 0 ? "." : ":") + s;
    }
    
    /**
     * Finds all movies that passed either the Peirce test OR the Landau test.
     * Returns a string that includes the number of movies that passed either 
     * test, as well as their titles, one movie per line.
     * 
     * @return a String representation of the movies that passed either the Peirce
     * or Landau test.
     */
    public String peirceOrLandauPass() {
        int count = 0;
        String s = "";

        for (Movie m : allMovies) {
            Vector<String> results = m.getAllTestResults();
            String peirce = results.get(1);
            String landau = results.get(2);

            if (peirce.equals("0") || landau.equals("0")) {
                count++;
                s += "\n\t" + m.getTitle();
            }
        }

        return s = count + " movie" + ((count == 1) ? "" : "s") + " passed the "
            + "Peirce test OR the Landau test" + ((count == 0) ? "." : ":") + s;
    }

    /**
     *  Finds all movies that passed the White test but did *not* pass the 
     *  Rees-Davies test. Returns a string that includes the number of movies 
     *  that passed the specifications, as well as their titles, one movie 
     *  per line.
     * 
     * @return  a String representation of the movies that passed the White 
     * test but did *not* pass the Rees-Davies test. 
     */
    public String whitePassRdFail() {
        int count = 0;
        String s = "";

        for (Movie m : allMovies) {
            Vector<String> results = m.getAllTestResults();
            String white = results.get(11);
            String reesDavies = results.get(12);

            if (white.equals("0") && reesDavies.equals("1")) {
                count++;
                s += "\n\t" + m.getTitle();
            }
        }

        return s = count + " movie" + ((count == 1) ? "" : "s") + " passed "
            + "the White test but did *not* pass the Rees-Davies test" 
            + ((count == 0) ? "." : ":") + s;
        //used two ternary operators here, to make the word "movies" plural/singular,
        //and to include a ':' instead of a period if there were movies found
    }
    
    /**
     * Enqueues all movies in the collection in a PriorityQueue based on their 
     * feminist score, then returns that PriorityQueue such that if dequeued,
     * the movies will be dequeued in priority order: from most feminist to 
     * least feminist.
     * 
     * @return  a PriorityQueue of all movies in the collection.
     */
    public PriorityQueue<Movie> rankMovies() {
        PriorityQueue<Movie> ranked = new PriorityQueue<Movie>();
        for (Movie m : allMovies) {
            ranked.enqueue(m);
        }

        return ranked;
    }

    /**
     * Returns a String representing this MovieCollection, including the total 
     * number of movies, number of actors, average feminist score of the movies
     * taken together, and the movies themselves ranked from most feminist to 
     * least feminist.
     * 
     * @return  a String representation of this collection
     */
    public String toString() {
        String s = "";
        
        //calculating the average feminist score of all the movies
        float meancounter = 0;
        for (Movie m : allMovies) {
            meancounter += m.feministScore();
        }
        meancounter = meancounter/allMovies.size();
        
        s += "There are " + allMovies.size() + " movies and " + allActors.size() 
            + " actors in this collection, and it has an average feminist score of " 
            + meancounter + "\nRanked in order from most feminist to least "
            + "feminist they are:\n";
            
        PriorityQueue<Movie> r = this.rankMovies();
        // dequeueing the elements in ranked order
        while (!r.isEmpty()) {
            s += "\t" + r.dequeue() + "\n";
        }
            
        return s;
    }

    /**
     * main method, used for testing
     */
    public static void main(String[] args) {
        System.out.println("------------------------------TESTING IN "
            + "MOVIE COLLECTION CLASS------------------------------");

        // didn't focus on testing any movies that have no Bechdel-alternative 
        // test results assosciated with them, as none existed in the files 
        // we're using (nothing was missing)

        System.out.println("[TESTING USING NONEXISTENT FILES (expect error and"
            + " appropriate message)]");
        MovieCollection mc0 = new MovieCollection("data/no.txt","data/exist.txt");

        System.out.println("\n[TRYING TO USE NONEXISTENT CAST FILE (expect error"
            + " message, but movies can still be printed, just without actors)]");
        MovieCollection mc1 = new MovieCollection("data/small_allTests.txt", 
                "no.txt");
        System.out.println(mc1);
        
        System.out.println("[TRYING TO USE NONEXISTENT TESTRESULTS FILE "
            + "(expect error message and no movies to be added)]");
        MovieCollection mc2 = new MovieCollection("nodata", 
                "data/small_castGender.txt");
        System.out.println(mc2);

        System.out.println("\n\n[INITIAL TESTING USING SMALLER FILES "
            + "(small_allTests.txt,small_castGender.txt)]");
        MovieCollection mc3 = new MovieCollection("data/small_allTests.txt", 
                "data/small_castGender.txt");

        System.out.println("[TESTING CONSTRUCTOR/READMOVIES/READCASTS/TOSTRING/"
            + "RANKMOVIES: EXPECTING 3 movies ordered (Gamma, Alpha, Beta), 5 actors]\n" 
            + mc3);
        System.out.println("[TESTING GETACTORNAMES: EXPECTING Tyler Perry, "
            + "Cassi Davis, Patrice Lovely, Stella, Takis.]\n    --> "
            + mc3.getActorNames());
        System.out.println("[TESTING GETACTORS: EXPECTING Tyler Perry (male)"
            + ", Cassi Davis (female), Patrice Lovely (female), Stella (male),"
            + "Takis (female).]\n    --> "+ mc3.getActors());
        System.out.println("[TESTING GETMOVIES: EXPECTING Alpha (4), Beta (4),"
            + " Gamma (2)]\n    --> " + mc3.getMovies());
        System.out.println("[TESTING GETMOVIETITLES: EXPECTING Alpha, Beta, "
            + "Gamma.]\n    --> " + mc3.getMovieTitles());
        System.out.println("[TESTING FINDALLMOVIESPASSEDTESTNUM(0): EXPECTING Alpha"
            + ", Beta, Gamma.]\n    --> " + mc3.findAllMoviesPassedTestNum(0));
        System.out.println("[TESTING FINDALLMOVIESPASSEDTESTNUM(3): EXPECTING "
            + "empty linkedlist.] --> " + mc3.findAllMoviesPassedTestNum(3));

        System.out.println("\n[TESTING WHITEPASSRDFAIL: EXPECTING 0 movies.]\n"
            + mc3.whitePassRdFail()); // fails both tests right now
        mc3.getMovies().get(2).setTestResults("0,0,0,1,0,0,0,1,0,0,1,0,1");
        System.out.println("\n[Changing 'Gamma' movie's test results. Should "
            + "now pass White test and fail Rees-Davies. TESTING WHITEPASSRDFAIL:"
            + " EXPECTING 1 movie.]\n" + mc3.whitePassRdFail());
        mc3.getMovies().get(1).setTestResults("0,0,0,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Beta' movie's test results. Should "
            + "now pass White test and pass Rees-Davies. TESTING WHITEPASSRDFAIL:"
            + " EXPECTING 1 movie.]\n" + mc3.whitePassRdFail());
        mc3.getMovies().get(0).setTestResults("0,0,0,1,0,0,0,1,0,0,1,0,1");
        System.out.println("\n[Changing 'Alpha' movie's test results. Should "
            + "now pass White test and fail Rees-Davies. TESTING WHITEPASSRDFAIL:"
            + " EXPECTING 2 movies.]\n" + mc3.whitePassRdFail());
        
        System.out.println("\n[TESTING PEIRCEORLANDAUPASS: EXPECTING 3 movies."
            + " (each movie should pass both tests right now)]\n" 
            + mc3.peirceOrLandauPass());
        mc3.getMovies().get(0).setTestResults("0,1,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Alpha' movie's test results. Should "
            + "now fail Peirce and fail Landau. TESTING PEIRCEORLANDAUPASS:"
            + " EXPECTING 2 movies.]\n" + mc3.peirceOrLandauPass());
        mc3.getMovies().get(1).setTestResults("0,1,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Beta' movie's test results. Should "
            + "now fail Peirce and fail Landau. TESTING PEIRCEORLANDAUPASS:"
            + " EXPECTING 1 movie.]\n" + mc3.peirceOrLandauPass());
        mc3.getMovies().get(2).setTestResults("0,1,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Gamma' movie's test results. Should "
            + "now fail Peirce and fail Landau. TESTING PEIRCEORLANDAUPASS:"
            + " EXPECTING 0 movies.]\n" + mc3.peirceOrLandauPass());
        mc3.getMovies().get(2).setTestResults("0,0,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Gamma' movie's test results. Should "
            + "now pass Peirce and fail Landau. TESTING PEIRCEORLANDAUPASS:"
            + " EXPECTING 1 movie.]\n" + mc3.peirceOrLandauPass());
        mc3.getMovies().get(1).setTestResults("0,1,0,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Beta' movie's test results. Should "
            + "now fail Peirce and pass Landau. TESTING PEIRCEORLANDAUPASS:"
            + " EXPECTING 2 movies.]\n" + mc3.peirceOrLandauPass());
        
        System.out.println("\n[TESTING BECHDELPASS: EXPECTING 3 movies.]\n"
            + mc3.bechdelPass());
        mc3.getMovies().get(0).setTestResults("1,1,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Alpha' movie's test results. Should "
            + "now fail Bechdel test. TESTING BECHDELPASS:"
            + " EXPECTING 2 movies.]\n" + mc3.bechdelPass());
        mc3.getMovies().get(1).setTestResults("1,1,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Beta' movie's test results. Should "
            + "now fail Bechdel test. TESTING BECHDELPASS:"
            + " EXPECTING 1 movie.]\n" + mc3.bechdelPass());
        mc3.getMovies().get(2).setTestResults("1,1,1,1,0,0,0,1,0,0,1,0,0");
        System.out.println("\n[Changing 'Gamma' movie's test results. Should "
            + "now fail Bechdel test. TESTING BECHDELPASS:"
            + " EXPECTING 0 movies.]\n" + mc3.bechdelPass());
        
        System.out.println("\n\n[TESTING USING COMPLETE FILES WITH REAL DATA "
            + "(should have 50 movies!, ranked according to their feminist]"
            + "\n[scores (and then ranked by # of actors if they tie, movies "
            + "with fewer actors being ranked first, and]\n[then ranked "
            + "alphabetically if they tie on the # of actors), expect average "
            + "to be 47.9]");
        MovieCollection mc4 = new MovieCollection("data/nextBechdel_allTests.txt", 
                "data/nextBechdel_castGender.txt");
        System.out.println(mc4);
        
        System.out.println("\n[Making sure that calling the toString works "
            + "consistently. Expect same output as above]");
        System.out.println(mc4 + "\n");
        
        System.out.println("[TESTING FINDALLMOVIESPASSEDTESTNUM(8): EXPECTING "
            + "5 movies.]\n" + mc4.findAllMoviesPassedTestNum(8).size());
        System.out.println("\n[TESTING WHITEPASSRDFAIL: EXPECTING 0 movies.]");
        System.out.println(mc4.whitePassRdFail());

        System.out.println("\n[TESTING PEIRCEORLANDAUPASS: EXPECTING 47 movies.]");
        System.out.println(mc4.peirceOrLandauPass());

        System.out.println("\n[TESTING BECHDELPASS: EXPECTING 32 movies.]");
        System.out.println(mc4.bechdelPass());
    }
}