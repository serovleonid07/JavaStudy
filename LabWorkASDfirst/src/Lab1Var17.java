public class Lab1Var17 {

    // ================== СЛИЯНИЕ ==================

    public static Queue mergeQueues(Queue q1, Queue q2) {
        Queue result = new Queue();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (q1.peek() <= q2.peek())
                result.enqueue(q1.dequeue());
            else
                result.enqueue(q2.dequeue());
        }
        while (!q1.isEmpty())
            result.enqueue(q1.dequeue());
        while (!q2.isEmpty())
            result.enqueue(q2.dequeue());
        return result;
    }

    // ================== СОРТИРОВКА СПИСКА (in-place) ==================

    /**
     * Сортировка очереди по возрастанию сортировкой вставками,
     * работая напрямую со связным списком (без массива и без новых узлов).
     *
     * Идея:
     *   - sorted — голова уже отсортированной части (изначально null);
     *   - cur    — текущий узел из исходного списка;
     *   - для каждого cur ищем место в sorted и вставляем.
     */
    private static void sortQueueInPlace(Queue q) {
        Node sorted = null;
        Node cur = q.getFront();          // было: q.front

        while (cur != null) {
            Node next = cur.next;

            if (sorted == null || cur.data < sorted.data) {
                cur.next = sorted;
                sorted = cur;
            } else {
                Node p = sorted;
                while (p.next != null && p.next.data < cur.data) {
                    p = p.next;
                }
                cur.next = p.next;
                p.next = cur;
            }

            cur = next;
        }

        q.setFront(sorted);               // было: q.front = sorted

        Node last = sorted;
        while (last != null && last.next != null) {
            last = last.next;
        }
        q.setTail(last);                  // было: q.tail = last
    }

    // ================== ПРОВЕРКА И ПАРСИНГ ЧИСЕЛ ==================

    /**
     * Является ли строка целым числом (возможен минус).
     * Своя реализация — без Integer.parseInt и без try/catch.
     */
    private static boolean isInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        int start = 0;
        if (s.charAt(0) == '-') {
            if (s.length() == 1) return false; // только "-"
            start = 1;
        }
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    /** Своё преобразование строки в int. */
    private static int stringToInt(String s) {
        boolean negative = false;
        int start = 0;
        if (s.charAt(0) == '-') {
            negative = true;
            start = 1;
        }
        int result = 0;
        for (int i = start; i < s.length(); i++) {
            result = result * 10 + (s.charAt(i) - '0');
        }
        return negative ? -result : result;
    }

    // ================== ВВОД БЕЗ СТОРОННИХ БИБЛИОТЕК ==================

    /** Читает одну строку из System.in побайтово. null — конец потока. */
    private static String readLine() {
        StringBuilder sb = new StringBuilder();
        try {
            int ch;
            boolean hasData = false;
            while ((ch = System.in.read()) != -1) {
                hasData = true;
                if (ch == '\n') break;
                if (ch == '\r') continue; // Windows
                sb.append((char) ch);
            }
            if (!hasData && sb.length() == 0) return null;
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }

    /**
     * Заполняет очередь: пользователь вводит числа через пробел в одной строке.
     * Нечисловые токены пропускаются с предупреждением.
     */
    private static Queue readQueue(String prompt) {
        Queue queue = new Queue();
        System.out.println(prompt);
        System.out.print("Введите целые числа через пробел: ");

        String line = readLine();
        if (line == null || line.trim().isEmpty()) {
            System.out.println("Пустая строка — очередь останется пустой.");
            return queue;
        }

        // Ручное разбиение на токены (без String.split)
        StringBuilder token = new StringBuilder();
        for (int i = 0; i <= line.length(); i++) {
            char c = (i < line.length()) ? line.charAt(i) : ' '; // виртуальный пробел в конце
            if (c == ' ' || c == '\t') {
                if (token.length() > 0) {
                    String t = token.toString();
                    if (isInteger(t)) {
                        queue.enqueue(stringToInt(t));
                    } else {
                        System.out.println("Пропущено (не число): \"" + t + "\"");
                    }
                    token.setLength(0);
                }
            } else {
                token.append(c);
            }
        }
        return queue;
    }

    // ================== MAIN ==================

    public static void main(String[] args) {
        Queue q1 = readQueue("=== Заполнение первой очереди ===");
        Queue q2 = readQueue("=== Заполнение второй очереди ===");

        System.out.println("\nДо сортировки:");
        System.out.print("q1: "); q1.print();
        System.out.print("q2: "); q2.print();

        sortQueueInPlace(q1);
        sortQueueInPlace(q2);

        System.out.println("\nПосле сортировки:");
        System.out.print("q1: "); q1.print();
        System.out.print("q2: "); q2.print();

        System.out.println("\nРезультат слияния:");
        mergeQueues(q1, q2).print();
    }
}