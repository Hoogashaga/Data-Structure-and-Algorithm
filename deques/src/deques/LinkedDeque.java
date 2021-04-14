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
        front.next.prev = temp;
        front.next = temp;
    }

    public void addLast(T item) {
        size += 1;
        Node<T> temp = new Node<>(item);
        temp.next = back;
        back.prev.next = temp;
        temp.prev = back.prev;
        back.prev = temp;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> first = front.next;
        front.next = first.next;
        first.next.prev = front;
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
        if (size == 0) {
            return null;
        }
        int cnt = 0;
        Node<T> cur = front.next;
        while (index > 0) {
            cur = cur.next;
            index -= 1;
        }
        return cur.value;
    }

    public int size() {
        return size;
    }
}
