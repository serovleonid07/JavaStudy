public class Queue {
    private Node front;
    private Node tail;

    public Queue() {
        front = null;
        tail = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void enqueue(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            front = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Очередь пуста!");
            return -1;
        }
        int value = front.data;
        front = front.next;
        if (front == null)
            tail = null;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Очередь пуста!");
            return -1;
        }
        return front.data;
    }

    public void print() {
        Node cur = front;
        while (cur != null) {
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    // ===== ДОБАВЛЕНО ДЛЯ СОРТИРОВКИ =====

    /** Голова списка — нужна для обхода извне. */
    public Node getFront() {
        return front;
    }

    /** Установить новую голову (после сортировки). */
    public void setFront(Node front) {
        this.front = front;
    }

    /** Установить новый хвост (после сортировки). */
    public void setTail(Node tail) {
        this.tail = tail;
    }
}