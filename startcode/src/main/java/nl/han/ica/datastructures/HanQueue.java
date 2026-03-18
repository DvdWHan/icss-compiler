package nl.han.ica.datastructures;

import lombok.AllArgsConstructor;

import java.util.NoSuchElementException;

public class HanQueue<T> implements IHanQueue<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  @Override
  public void clear() {
    head = null;
    tail = null;
    size = 0;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public T peek() {
    return head != null ? head.value : null;
  }

  @Override
  public void enqueue(T value) {
    if (isEmpty()) {
      head = tail = new Node<>(value, null);
    } else {
      tail.next = new Node<>(value, null);
      tail = tail.next;
    }
    ++size;
  }

  @Override
  public T dequeue() {
    ensureNotEmpty();
    T result = peek();
    head = head.next;
    --size;
    return result;
  }

  @AllArgsConstructor
  private static class Node<T> {
    private T value;
    private Node<T> next;
  }

  private void ensureNotEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
  }

  public static void main(String[] args) {
    var q =  new HanQueue<String>();
    System.out.println(q.peek());
  }
}
