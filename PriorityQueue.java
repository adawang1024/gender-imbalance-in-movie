package javafoundations;
import javafoundations.exceptions.*;

/**
 * This is a structure based on a queue, that dequeues elements based on the 
 * highest priority (it does not follow 'first in, first out'). This 
 * PriorityQueue implementation is based on a LinkedMaxHeap, which, in turn, 
 * implements the MaxHeap interface. 
 *
 * @author Genevieve Mellott, Ada Wang, Anna Zhou
 * @version 12/16/2022
 */
public class PriorityQueue<T extends Comparable<T>> implements Queue<T> {
    private LinkedMaxHeap<T> heap;
    
    /**
     * Constructor for objects of class PriorityQueue. Creates an empty
     * priority queue.
     */
    public PriorityQueue() { 
        heap = new LinkedMaxHeap<T>();
    }
    
    /**
     * Adds the specified element to the rear of the priority queue.
     * 
     * @param  element  the element to be added
     */
    public void enqueue(T element) { 
        heap.add(element);
    }

    /**
     * Removes and returns the element at the front of the priority queue.
     * 
     * @return  the element removed from the front of the priority queue
     */
    public T dequeue() { 
        try {
            T temp = heap.removeMax();
            return temp;
        } catch(EmptyCollectionException ece) {
            System.out.println(ece);
        }
        return null;
    }

    /**
     * Returns a reference to the element at the front of the priority queue 
     * without removing it.
     * 
     * @return  a reference to the element at the front of the priority queue
     */
    public T first() { 
        return heap.getMax();
    }

    /**
     * Returns true if the priority queue contains no elements and false
     * otherwise.
     * 
     * @return  true if the priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns the number of elements in the priority queue.
     * 
     * @return  the number of elements in the priority queue
     */
    public int size() {
         return heap.size();
    }

    /**
     * Returns a string representation of the priority queue.
     * 
     * @return  a string representation of the priority queue
     */
    public String toString() { 
        return heap.toString();
    }
}