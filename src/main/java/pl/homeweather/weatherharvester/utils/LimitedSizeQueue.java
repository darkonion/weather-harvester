package pl.homeweather.weatherharvester.utils;

import java.util.*;

public class LimitedSizeQueue<T> implements Deque<T> {

    private final int sizeLimit;

    private final Deque<T> deque;

    public LimitedSizeQueue(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        this.deque = new LinkedList<>();
    }

    @Override
    public void addFirst(T t) {
        if (deque.size() >= sizeLimit) {
            deque.removeLast();
        }
        deque.addFirst(t);
    }

    @Override
    public void addLast(T t) {
        if (deque.size() >= sizeLimit) {
            deque.removeFirst();
        }
        deque.addLast(t);
    }

    @Override
    public boolean offerFirst(T t) {
        return deque.offerFirst(t);
    }

    @Override
    public boolean offerLast(T t) {
        return deque.offerLast(t);
    }

    @Override
    public T removeFirst() {
        return deque.removeFirst();
    }

    @Override
    public T removeLast() {
        return deque.removeLast();
    }

    @Override
    public T pollFirst() {
        return deque.pollFirst();
    }

    @Override
    public T pollLast() {
        return deque.pollLast();
    }

    @Override
    public T getFirst() {
        return deque.getFirst();
    }

    @Override
    public T getLast() {
        return deque.getLast();
    }

    @Override
    public T peekFirst() {
        return deque.peekFirst();
    }

    @Override
    public T peekLast() {
        return deque.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return deque.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return deque.removeLastOccurrence(o);
    }

    @Override
    public boolean add(T t) {
        if (deque.size() >= sizeLimit) {
            deque.removeLast();
        }
        deque.addFirst(t);
        return true;
    }

    @Override
    public boolean offer(T t) {
        return false;
    }

    @Override
    public T remove() {
        return deque.remove();
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T element() {
        return deque.element();
    }

    @Override
    public T peek() {
        return deque.peek();
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        deque.clear();
    }

    @Override
    public void push(T t) {
        if (deque.size() >= sizeLimit) {
            deque.removeFirst();
        }
        deque.addLast(t);
    }

    @Override
    public T pop() {
        return deque.pop();
    }

    @Override
    public boolean remove(Object o) {
        return deque.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return deque.containsAll(collection);
    }

    @Override
    public boolean contains(Object o) {
        return deque.contains(o);
    }

    @Override
    public int size() {
        return deque.size();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return deque.iterator();
    }

    @Override
    public Object[] toArray() {
        return deque.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return deque.toArray(t1s);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return deque.descendingIterator();
    }
}
