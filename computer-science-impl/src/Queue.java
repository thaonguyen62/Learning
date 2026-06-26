
public class Queue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T>{
        T data;
        Node<T> next;
        Node(T data){
            this.data = data;
        }
    }

    public Queue(){
        head = tail = null;
        size = 0;
    }

    public boolean add(T data){
        Node<T> newNode = new Node<>(data);
        if(head == null){
            head = tail = newNode;
        } else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }


    public T poll(){
        if(head == null) return null;
        T data = head.data;
        head = head.next;
        if(head == null) tail = null;
        size--;
        return data;
    }


    public T peek(){
        if(head == null) return null;
        return head.data;
    }

    public boolean contains(Object o){
        Node<T> curr = head;
        while(curr != null){
            if(curr.data == null ? o == null : curr.data.equals(o)) return true;
            curr = curr.next;
        }
        return false;
    }

    public void clear(){
        head = tail = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
}
