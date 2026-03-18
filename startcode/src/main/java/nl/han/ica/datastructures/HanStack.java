package nl.han.ica.datastructures;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.EmptyStackException;

@NoArgsConstructor
public class HanStack<T> implements IHanStack<T> {
  private Node<T> top = null;
  private int size = 0;

  @Override
  public T peek() {
    ensureNotEmpty();
    return top.value;
  }

  @Override
  public void push(T value) {
    top = new Node<>(value, top);
    ++size;
  }

  @Override
  public T pop() {
    T result = peek();
    Node<T> newTop = top.next;
    top.next = null;
    top = newTop;
    --size;
    return result;
  }

  @AllArgsConstructor
  private static class Node<T> {
    public T value;
    public Node<T> next;
  }

  private void ensureNotEmpty() {
    if (size == 0) {
      throw new EmptyStackException();
    }
  }
}
