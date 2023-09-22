package org.vsdl.common.engine.utils;

import java.util.Objects;
import java.util.PriorityQueue;

public class ProtectedPriorityQueue<E extends Comparable<E>> {
    private final PriorityQueue<E> queue;

    public ProtectedPriorityQueue() {
        this.queue = new PriorityQueue<>();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public E peek() {
        return queue.peek();
    }

    public synchronized E alter(E e) {
        if (Objects.isNull(e)) {
            return queue.remove();
        } else {
            queue.add(e);
            return null;
        }
    }
}