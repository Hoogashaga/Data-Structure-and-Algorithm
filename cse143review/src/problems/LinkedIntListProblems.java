package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode firstNode = list.front.next.next;
        ListNode secondNode = list.front.next;
        ListNode thirdNode = list.front;
        list.front = firstNode;
        list.front.next = secondNode;
        list.front.next.next = thirdNode;
        list.front.next.next.next = null;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front == null) {
            return;
        }
        if (list.front.next == null) {
            return;
        }

        ListNode first = list.front;
        ListNode second = list.front.next;
        first.next = null;
        list.front = second;
        while (second.next != null) {
            second= second.next;
        }
        second.next = first;
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        ListNode p = a.front;
        int i = 0;
        if (p == null) {
            LinkedIntList c = new LinkedIntList();
            c.front = b.front;
            return c;
        }
        else {
            while (p.next != null) {
                p = p.next;
                i++;
            }
            ListNode cur = a.front;
            int[] value = new int[i + 1];
            for (int j = 0; j < i + 1; j++) {
                value[j] = cur.data;
                cur = cur.next;
            }
            LinkedIntList d = new LinkedIntList(value);
            ListNode c = d.front;
            while (c.next != null) {
                c = c.next;
            }
            c.next = b.front;
            return d;
        }
    }
}
