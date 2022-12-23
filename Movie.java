import java.util.*;
import java.io.*;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and results for the thirteen 
 * gender imbalance tests (the original Bechdel test and the 12 alternatives).
 *
 * @author Genevieve Mellott, Ada Wang, & Anna Zhou (CS230 Instructors)
 * @version 12/16/22
 */
public class Movie implements Comparable<Movie> {
    private String title;
    private Hashtable<Actor, String> allActors;
    private Vector<String> testResults;
    private int femScore;

    /**
     * Constructor for objects of class Movie.
     * 
     * @param  title  The title of the movie
     */
    public Movie(String title) {
        this.title = title;
        allActors = new Hashtable<Actor,String>(100);
        testResults = new Vector<String>();
    }

    /**
     * Returns the movie's title
     *
     * @return  The title of this movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a Linked List with all the actor names who played in this 
     * movie.
     *
     * @return  A LinkedList with the names of all the actors who played 
     * in this movie
     */
    public LinkedList<String> getActors() {
        LinkedList<String> actorNames = new LinkedList<String>();
        
        // get all the Actors in the movie, and add only their name
        // to the new linked list
        for (Enumeration<Actor> e = allActors.keys(); e.hasMoreElements();) {
            Actor a = e.nextElement();
            actorNames.add(a.getName());
        }
        
        return actorNames;
    }

    /**
     * Returns the movie's actors in a Hashtable
     *
     * @return  A Hashtable with all the actors who played in this movie
     */
    public Hashtable<Actor,String> getAllActors() {
        return allActors;
    }

    /**
     * Returns a Vector with all 13 gender imbalance test results for this 
     * movie.
     *
     * @return  A Vector with the test results for this movie: A test result 
     * can be "0" or "1" indicating that this move passed or did not pass the 
     * corresponding test.
     */
    public Vector<String> getAllTestResults() {
        return testResults;
    }

    /**
     * This method sets a movie's test results. It takes as input a String 
     * (such as "0,0,0,1,0,0,0,1,0,0,1,1,1") and populates the testResults Vector 
     * instance variable accordingly. Note that the String parameter represents 
     * the thirteen Bechdel-alternative test results on this movie, with 
     * values “0” or “1”, where “0” means the movie has passed the test, and 
     * “1” means the movie has not passed the test. 
     *
     * @param  results  A string consisting of 0's and 1's. Each one of 
     * these values denotes the result of the corresponding test on this movie
     */
    public void setTestResults(String results) {
        testResults.clear();
        String[] tests = results.split(",");

        // add each test result to the vector
        for (String value : tests) { 
            testResults.add(value);
        }
    }

    /**
     * Tests this movie object with the input one and determines whether they 
     * are equal.
     * 
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return this.title.equals(((Movie) other).title);
            // Need explicit (Movie) cast to use .title
        } else {
            return false;
        }
    }

    /**
     * This method takes in a String, formatted as lines are in the input file, 
     * generates an Actor, and adds the object to the actors of this movie (by
     * adding an appropriate entry to the Hashtable of Actors for this movie). 
     * The input String has the following formatting: ""ACTOR","CHARACTER_NAME",
     * "TYPE","BILLING","GENDER""
     * Example of input: ""Ricky Dillon","Aspen Heitz","Supporting","18","Male""
     * 
     * @param  line  A String representing the information of each Actor
     * @return  The Actor that was just added to this movie
     */
    public Actor addOneActor(String line) {
        line = line.substring(1, line.length()-1);
        // use substring, to get rid of extra double quotation marks at 
        // start and end of input string

        String[] info = line.split("\",\"");
        Actor a = new Actor(info[0], info[4]);

        allActors.put(a, info[2]);
        // the key is an Actor, and the value is the type of the role 
        // this Actor holds in this movie (for example, “supporting”)

        return a;
    }

    /**
     * Reads the input file with information on the cast, and adds all its 
     * Actors to this movie. Each line in the file has the following formatting: 
     * "MOVIE TITLE","ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER"
     * This method reads line by line from the file, and calls addOneActor() 
     * when needed, to add one actor at a time. This method is an instance 
     * method on Movie, and when it reads the file, it ignores lines related 
     * to other movies. Assumes that each Actor listed in the file is unique
     * (has a unique name), is billed once, and that their information is 
     * formatted correctly in the file.
     * 
     * @param  actorsFile  The file containing information on each actor 
     * who acted in the movie.
     */
    public void addAllActors(String actorsFile) {
        try {
            Scanner scan = new Scanner(new File(actorsFile));

            while (scan.hasNext()) {
                scan.useDelimiter(",");
                String t = scan.next();
                // find movie title listed in file
                String line = scan.nextLine();
                // find the remaining info. on one line

                // see if movie title matches what we're looking for
                if (t.equals("\"" + this.title + "\"")) {
                    addOneActor(line.substring(1));
                } 
            }
            scan.close();
        } catch (IOException ex) {
            System.out.println("Error trying to use file named '" + actorsFile 
                + "'\n\t" + ex);
        }
    }

    /**
     * This method determines and returns, according to our definition, the 
     * movie’s feminist score. Our definition of a feminist movie depends on 
     * a combination of the results of six specific Bechdel-alternative tests. 
     * Our feminist score is calculated as a weighted score out of 100, with
     * passing/failing the Bechdel Test counting for 10%, the Rees Davies 
     * for 20%, the Ko Test(5%), the Peirce Test(30%), the Feldman Score(25%), 
     * and the Koeze-Dottle(10%).
     * 
     * @return  the movie's feminist score
     */
    public int feministScore() {
        Vector<String> results = this.getAllTestResults();
        int finalScore = 0;
        
        //checking Bechdel results below, "0" means it passed
        if (results.get(0).equals("0"))
            finalScore += 10;
        if (results.get(12).equals("0")) //checking Rees Davies
            finalScore += 20;
        if (results.get(6).equals("0")) //Ko test 
            finalScore += 5;
        if (results.get(1).equals("0")) //Pierce
            finalScore += 30;
        if (results.get(3).equals("0")) //Feldman
            finalScore += 25;
        if (results.get(9).equals("0")) //Koeze-Dottle
            finalScore += 10;
        
        femScore = finalScore;//setting instance variable to hold the feminist score
        return finalScore;
    }
    
    /**
     * Compares Movies based on their feminist score. Returns 1 if the 
     * feminist score of this movie is higher than that of the other movie, 
     * or returns -1 if it is lower. If they are equal, it compares them based
     * on the number of actors in each movie (returns 1 if this movie has 
     * fewer actors, returns -1 if the other movie has fewer actors).
     * If there is still a tie and they have the same number of actors, 
     * it compares the movie's titles lexicographically.
     * 
     * @param  other  The other movie that this movie is being compared to
     * @return 1 if the movie is to be ranked higher than the other one, -1 otherwise.
     */
    public int compareTo(Movie other) {
        if (this.femScore > other.femScore) {
            return 1;
        } else if (this.femScore == other.femScore) {
            //if feminist scores are equal, compares their number of actors
            if (this.allActors.size() > other.allActors.size()) {
                return -1;
            } else if (this.allActors.size() < other.allActors.size()) {
                return 1;
                // movies with fewer actors will rank higher
            }
            //if equal in number of actors, compares them lexicographically 
            //(earlier in the alphabet ranks higher).
            return this.getTitle().compareToIgnoreCase(other.getTitle()) * -1;
        } else {
            return -1; // other movie has higher feminist score
        }
    }

    /**
     * Returns a string representation of this movie (includes the title of 
     * the movie and the number of actors in it, and the feminist score).
     * 
     * @return a reasonable string representation of this movie: includes the 
     * title, the number of actors who played in it, and the feminist score.
     */
    public String toString() {
        return title + " (" + allActors.size() + " actors, and a feminist"
            + " score of " + femScore + ")";
    }

    /**
     * main method, used for testing
     */
    public static void main(java.lang.String[] args) {
        System.out.println("------------------------------"
            + "TESTING IN MOVIE CLASS------------------------------");
        
        Movie m1 = new Movie("Alpha");
        
        System.out.println("[Trying to read from a file that doesn't exist."
            + " Expect an appropriate error message]");
        m1.addAllActors("doesntExist.txt");
        
        System.out.println("\n[Testing getActors() on a Movie, when actors "
            + "haven't been added yet. Expect it to return an empty linked"
            + "list] --> " + m1.getActors());
        System.out.println("[Testing getAllActors() on a Movie, when actors"
            + " haven't been added yet. Expect it to return an empty hash"
            + "table] --> " + m1.getAllActors());

        m1.addAllActors("data/small_castGender.txt");
        System.out.println("\n[Initial Testing using small_castGender.txt]");
        System.out.println("[Testing addAllActors() on movie 'Alpha'. Expect 4 "
            + "actors (feminist score not calculated yet)] --> " + m1);
        System.out.println("[Expect getTitle() to return Alpha] --> " 
            + m1.getTitle());
        System.out.println("[Expect getActors() to return list containing"
            + " Tyler Perry, Cassi Davis, Patrice Lovely, and Stella] --> " 
            + m1.getActors());
        
        System.out.println("[Expect getAllTestResults() to return an empty"
            + " vector] --> " + m1.getAllTestResults());
        m1.setTestResults("0,0,1,1,1,1,0,1,1,1,1,1,1");
        System.out.println("[Setting test results to 0,0,1,1,1,1,0,1,1,1,1,1,1"
            + "] --> " + m1.getAllTestResults());
        System.out.println("[Expect feministScore() of movie with title 'Alpha'"
            + " to be 45] ---> " + m1.feministScore());
        m1.setTestResults("0,0,0,0,0,0,0,0,0,0,0,0,0");
        System.out.println("[Testing setTestResults method. Setting test "
            + "results to 0,0,0,0,0,0,0,0,0,0,0,0,0. Expect feministScore() "
            + "to return 100] --> " + m1.feministScore());
        m1.setTestResults("1,1,1,1,1,1,1,1,1,1,1,1,1");
        System.out.println("[Setting test results to 1,1,1,1,1,1,1,1,1,1,1,1,1."
            + " Expect feministScore of 0] --> " + m1.feministScore());
        m1.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        System.out.println("[Setting test results to 0,0,0,1,0,0,0,1,0,0,1,1,1."
            + " Expect feministScore of 55] --> " + m1.feministScore());
        m1.setTestResults("1,1,0,1,1,1,0,1,1,1,1,1,1");
        System.out.println("[Setting test results to 1,1,0,1,1,1,0,1,1,1,1,1,1."
            + " Expect feministScore of 5] --> " + m1.feministScore());
            
        Movie m2 = new Movie("Gamma");
        m2.addAllActors("data/small_castGender.txt");
        System.out.println("\n[Expect Gamma to have 2 actors (feminist score "
            + "not calculated yet)] --> " + m2);
        System.out.println("[Expect getAllActors to return hashtable "
            + "containing: (Tyler Perry, Male)=Leading, (Cassi Davis, Female)"
            + "=Supporting] --> " + m2.getAllActors());
        System.out.println("\n[Adding one actor, Jane Smith, to Gamma. "
            + "Expect addOneActor to return (Jane Smith, Female)] --> " 
            + m2.addOneActor("\"Jane Smith\",\"Holly\",\"Supporting\",\"20\","
            + "\"Female\""));
        System.out.println("[Expect Gamma to now have 3 actors] --> " + m2);
        System.out.println("[Expect getAllActors to now return hashtable "
            + "containing: (Tyler Perry, Male)=Leading, (Cassi Davis, Female)"
            + "=Supporting, (Jane Smith, Female)=Supporting] --> " 
            + m2.getAllActors());
        
        Movie m3 = new Movie("Beta");
        m3.addAllActors("data/small_castGender.txt");
        
        // testing compareTo()
        System.out.println("\n[Testing compareTo on Movies Alpha and Beta "
            + "(m1 and m3)]");
        m1.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        m3.setTestResults("0,0,0,1,0,0,0,1,0,0,1,1,1");
        m1.feministScore();
        m3.feministScore();
        System.out.println("[Setting test results and feminist scores equal"
            + " to each other (and they both have the same # of actors). "
            + "Expect m1.compareTo(m3) to return 1] --> " + m1.compareTo(m3));
        System.out.println("[With scores and total # of actors equal, expect "
            + "m3.compareTo(m1) to return -1] --> " + m3.compareTo(m1));
        m2.addOneActor("\"Bobby Bob\",\"Holly\",\"Supporting\",\"20\",\"Male\"");    
        System.out.println("[Adding 1 actor to m3 (but m1 and m3 still have "
            + "the same feminist score). Expect m3.compareTo(m1) to return -1]"
            + " --> " + m3.compareTo(m1));
        System.out.println("[Same score, but m3 has more actors. Expect "
            + "m1.compareTo(m3) to return 1] --> " + m1.compareTo(m3));
        m3.setTestResults("1,1,1,1,1,1,1,1,1,0,1,1,1");
        m3.feministScore();
        System.out.println("[Changing m3 to have a lower feminist score. "
            + "Expect m1.compareTo(m3) to return 1] --> " + m1.compareTo(m3));
        System.out.println("[Since m3 has lower feminist score, expect "
            + "m3.compareTo(m1) to return -1] --> " + m3.compareTo(m1)); 
        
        System.out.println("\n\n[Testing using nextBechdel_castGender.txt]");
        System.out.println("[Testing addAllActors, (feminist scores not "
            + "calculated yet)]");
            
        Movie m4 = new Movie("Boo! A Madea Halloween");
        m4.addAllActors("data/nextBechdel_castGender.txt");
        System.out.println("[Expect 42 actors in 'Boo! A Madea Halloween'] "
            + "--> " + m4);
        Movie m5 = new Movie("X-Men: Apocalypse");
        m5.addAllActors("data/nextBechdel_castGender.txt");
        m5.setTestResults("0,0,0,1,0,1,1,1,1,1,1,1,1");
        System.out.println("[Expect 90 actors in 'X-Men: Apocalypse'] "
            + "--> " + m5);
        Movie m6 = new Movie("Fantastic Beasts and Where to Find Them");
        m6.addAllActors("data/nextBechdel_castGender.txt");
        m6.setTestResults("0,0,1,1,0,1,0,1,1,1,1,1,1");
        System.out.println("[Expect 61 actors in 'Fantastic Beasts and Where"
            + " to Find Them'] --> " + m6);
            
        System.out.println("\n[TESTING FEMINISTSCORE]");
        System.out.println("Expect X-Men to have feminist score of 40 --> " 
            + m5.feministScore());
        System.out.println("Expect Fantastic Beasts to have feminist score of"
            + " 45 --> " + m6.feministScore());
        
        System.out.println("\n[TESTING COMPARETO]");
        System.out.println("X-Men should be ranked lower than Fantastic Beasts,"
            +" expecting -1: " + m5.compareTo(m6));
        System.out.println("Fantastic Beasts should be ranked higher than X-Men,"
            + " expecting 1: " + m6.compareTo(m5));
    }
}