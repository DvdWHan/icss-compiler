package nl.han.ica.datastructures;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class HanLinkedList<T> implements IHanLinkedList<T> {
  private Node<T> first = null;
  private Node<T> last = null;
  private int size = 0;

  @Override
  public void clear() {
    first = null;
    last = null;
    size = 0;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public T getFirst() {
    return Objects.requireNonNull(first).value;
  }

  @Override
  public void addFirst(T value) {
    attachFirst(value);
  }

  @Override
  public void removeFirst() {
    detachFirst();
  }

  @Override
  public T get(int index) {
    ensureIndexInBounds(index);
    return getNode(index).value;
  }

  @Override
  public void insert(int index, T value) {
    ensureIndexInBounds(index);
    if (index == size) {
      attachLast(value);
    } else {
      attachBefore(index, value);
    }
  }

  @Override
  public void delete(int index) {
    ensureIndexInBounds(index);
    detach(getNode(index));
  }

  @AllArgsConstructor
  private static class Node<T> {
    private T value;
    private Node<T> next;
    private Node<T> previous;
  }

  private void ensureIndexInBounds(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
  }

  private void attachFirst(T value) {
    Node<T> oldFirst = first;
    Node<T> newFirst = new Node<>(value, first, null);
    first = newFirst;
    if (oldFirst == null) {
      last = newFirst;
    } else {
      oldFirst.previous = newFirst;
    }
    ++size;
  }

  private void attachLast(T value) {
    Node<T> oldLast = last;
    Node<T> newLast = new Node<>(value, oldLast, null);
    last = newLast;
    if (oldLast == null) {
      first = newLast;
    } else {
      oldLast.next = newLast;
    }
    ++size;
  }

  private void attachBefore(int index, T value) {
    Node<T> oldNode = getNode(index);
    Node<T> oldPrevious = oldNode.previous;
    Node<T> newNode = new Node<>(value, oldPrevious, oldNode);
    if (oldPrevious == null) {
      first = newNode;
    } else {
      oldPrevious.next = newNode;
    }
    ++size;
  }

  private Node<T> getNode(int index) {
    Node<T> current;
    if (index < (size >> 1)) {
      current = first;
      for (int i = 0; i < index; ++i) {
        current = current.next;
      }
    } else {
      current = last;
      for (int i = size - 1; i > index; --i) {
        current = current.previous;
      }
    }
    return current;
  }

  private void detachFirst() {
    Objects.requireNonNull(first);
    Node<T> newFirst = first.next;
    first.value = null;
    first.next = null;
    first = newFirst;
    if (newFirst == null) {
      last = null;
    } else {
      newFirst.previous = null;
    }
    --size;
  }

  private void detach(Node<T> node) {
    Node<T> next = node.next;
    Node<T> previous = node.previous;
    if (previous == null) {
      first = next;
    } else {
      previous.next = next;
      node.previous = null;
    }
    if (next == null) {
      last = previous;
    } else {
      next.previous = previous;
      node.next = null;
    }
    --size;
  }
}
