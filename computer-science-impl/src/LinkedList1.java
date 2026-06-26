public class LinkedList1<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedList1(){
        head = tail = null;
        size = 0;
    }

    public void add(T data){
        Node<T> node = new Node<>(data);
        if(head == null){
            head = tail = node;
        }else{
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void addFirst(T data){
        Node<T> node = new Node<>(data);
        if(head == null){
            head = tail = node;
        } else{
            node.next = head;
            head = node;
        }
    }

    public void addLast(T data){
        Node<T> node = new Node<>(data);
        if(head == null && tail == null){
            head = tail = node;
        } else{
            tail.next = node;
            tail = node;
        }
    }

    public void add(T data, int index){
        if(size > index){
            Node<T> curNode = head;
            Node<T> addNode = new Node<>(data);
            for(int i = 0; i < size; i++){
                if(i == index){
                    Node temp = curNode.next;
                    curNode.next = addNode;
                    addNode.next = temp;
                    size++;
                    return;
                }
                curNode = curNode.next;
            }
        } else if (size-1 == index) {
            addLast(data);
        } else if (size > 0 && index == 0) {
            addFirst(data);
        }
    }

    public boolean remove(T data){
        if(head == null) return false;
        if (head.data == data){
            head = head.next;
            size--;
            return true;
        }
        Node<T> cur = head;
        while(cur.next != null){
            if(cur.next.data.equals(data)){
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }
}
