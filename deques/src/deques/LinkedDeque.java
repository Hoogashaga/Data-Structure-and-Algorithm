package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;

    public LinkedDeque() {
        size = 0;
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        size += 1;
        Node<T> temp = new Node<>(item);
        temp.prev = front;
        temp.next = front.next;
        front.next = temp;
        temp.next.prev = temp;

    }

    public void addLast(T item) {
        size += 1;
        Node<T> temp = new Node<>(item);
        temp.next = back;
        temp.prev = back.prev;
        back.prev = temp;
        temp.prev.next = temp;

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> first = front.next;
        Node<T> first2 = front.next.next;
        front.next = first2;
        first2.prev = front;
        // front.next = first.next;
        // first.next.prev = front;
        return first.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> last = back.prev;
        Node<T> last2 = back.prev.prev;
        last2.next = back;
        back.prev = last2;
        return last.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        if (index == 0) {
            return front.next.value;
        }
        int mid = size / 2;

        Node<T> cur;
        if (index < mid) {
            cur = front;
            int cnt = 0;
            while (cnt <= index) {
                cur = cur.next;
                cnt += 1;
            }
        } else {
            cur = back;
            int cnt = size - 1;
            while (cnt >= index) {
                cur = cur.prev;
                cnt -= 1;
            }
        }
        return cur.value;
    }

    public int size() {
        return size;
    }
}
