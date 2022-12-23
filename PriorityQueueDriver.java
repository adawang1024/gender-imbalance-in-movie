import javafoundations.exceptions.*;
import javafoundations.*;

/**
 * PriorityQueueDriver is the driver program used to check that the 
 * implementation of the PriorityQueue class is correct.
 *
 * @author Genevieve Mellott, Ada Wang, Anna Zhou
 * @version 12/16/22
 */
public class PriorityQueueDriver {
    /**
     * main method, used for testing the PriorityQueue implementation
     */
    public static void main (String[] args) {
        System.out.println("------------------------------TESTING IN "
            + "PRIORITY QUEUE DRIVER------------------------------");
        
        PriorityQueue<Integer> test1 = new PriorityQueue<Integer>();
        
        System.out.println("[Creating an empty priority queue for testing]\n");
        System.out.println("[Expect isEmpty() to return true] --> " 
            + test1.isEmpty());
        System.out.println("[Expect size() to return 0] --> " + test1.size());

        System.out.println("\n[Expect first() to throw an EmptyCollectionException]");
        try {
            test1.first();
        } catch (EmptyCollectionException ex){ 
            System.out.println(ex);
        }
        
        System.out.println("\n[Expect dequeue() to throw an EmptyCollection"
            + "Exception]");
        try {
            test1.dequeue();
        } catch (EmptyCollectionException ex){ 
            System.out.println(ex);
        }
        
        System.out.println("\n[Enqueueing 3, 35, 40, -5, 2, 90, and 0 in that "
            + "order]");
        test1.enqueue(3);
        test1.enqueue(35);
        test1.enqueue(40);     
        test1.enqueue(-5);
        test1.enqueue(2);
        test1.enqueue(90);
        test1.enqueue(0);
        System.out.println("[Expect toString to return -5,3,2,90,35,40,0 in"
            + " that order]\n" + test1);
        
        System.out.println("[Expect size() to return 7] --> " + test1.size());
        System.out.println("[Expect isEmpty() to return false] --> " 
            + test1.isEmpty());
        System.out.println("[Expect first() to return 90] --> " + test1.first());
        
        System.out.println("\n[Testing dequeuing elements, should be dequeued"
            + " in order from greatest to smallest: 90,40,35,3,2,0,-5]");
        while (!test1.isEmpty()) {
            System.out.println(test1.dequeue());
        }
    }
}