/**
 * Represents an object of type Actor. An Actor has a name and a gender.
 *
 * @author Genevieve Mellott, Ada Wang, & Anna Zhou (CS230 Instructors)
 * @version 12/16/22
 */
public class Actor {
    private String name;
    private String gender;

    /**
     * Constructor for objects of class Actor.
     * 
     * @param  name  The name of the actor
     * @param  gender  The gender of the actor
     */
    public Actor(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    /**
     * Returns the gender of this actor
     *
     * @return  The gender of this actor
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the name of this actor
     *
     * @return  The name of this actor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the gender of this actor
     *
     * @param  g  The gender of this actor
     */
    public void setGender(String g) {
        gender = g;
    }

    /**
     * Sets the name of this actor
     *
     * @param  n  The name of this actor
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * This method is defined here because Actor (mutable) is used as a key 
     * in a Hashtable. It makes sure that the same Actors always have the 
     * same hash code. So, the hash code of any object that is used as key in 
     * a hash table, has to be produced on an *immutable* quantity, like a 
     * String (such a string is the name of the actor in our case)
     * 
     * @return an integer, which is the hash code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Tests this actor against the input one and determines whether they are equal.
     * Two actors are considered equal if they have the same name and gender.
     * 
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this Actor.
     * 
     * @return a reasonable string representation of this actor, containing 
     * their name and gender.
     */
    public String toString() {
        return "(" + name + ", " + gender + ")";
    }

    /**
     * A main() method, used for some basic testing
     */
    public static void main (String[] args) {
        System.out.println("------------------------------"
            + "TESTING IN ACTOR CLASS------------------------------");
        
        Actor a = new Actor("Jane Smith", "Female");
        System.out.println("[Testing constructor by entering new actor. "
            + "Expect 'Jane Smith, Female'] --> "+ a);
        System.out.println("[Testing getGender. Expect 'Female'] --> " 
            + a.getGender());
        System.out.println("[Testing getName. Expect 'Jane Smith'] --> " 
            + a.getName());
        
        a.setGender("Male");
        System.out.println("\n[Testing setGender, setting to 'Male'. Expect "
            + "'Jane Smith, Male'] --> " + a);
        a.setName("John Brown");
        System.out.println("[Testing setName, setting to 'John Brown'. Expect"
            + " 'John Brown, Male'] --> " + a);
        System.out.println("[Testing getGender. Expect 'Male'] --> " 
            + a.getGender());
        System.out.println("[Testing getName. Expect 'John Brown'] --> " 
            + a.getName());
            
        Actor b = new Actor("Jane Smith", "Female");
        System.out.println("\n[Recreating Jane Smith as new actor. "
            + "Expect 'Jane Smith, Female'] --> "+ b);
        Actor c = new Actor("Jane Smith", "Female");
        Actor d = new Actor("Jane Smith", "Male");
        Actor e = new Actor("John Brown", "Female");
        
        System.out.println("\n[Testing equals. Expect 'false'] --> " 
            + a.equals(b));
        System.out.println("[Testing equals. Expect 'true'] --> " 
            + b.equals(c));
        System.out.println("[Testing equals. Expect 'false'] --> " 
            + b.equals(d));
        System.out.println("[Testing equals. Expect 'false'] --> " 
            + b.equals(e));
        
        System.out.println("\n[Testing hashcode. Expect unique integer] --> " 
            + a.hashCode());
        System.out.println("[Testing hashcode. Expect unique integer] --> " 
            + b.hashCode());
        System.out.println("[Testing hashcode. Expect same int as above] --> " 
            + c.hashCode());
        System.out.println("[Testing hashcode. Expect same int as above] --> " 
            + d.hashCode());
        System.out.println("[Testing hashcode. Expect same int as first] --> " 
            + e.hashCode());
    }
}