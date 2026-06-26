# Linked List

## What
Chain of nodes. Each node holds **data** + **pointer to next node**.
No index. No contiguous memory. Size dynamic.

```
[data|next] → [data|next] → [data|next] → None
  head                         tail
```

Variants:
- **Singly**: pointer to next only
- **Doubly**: pointer to next AND prev
- **Circular**: tail points back to head

---

## When to Use
- Frequent insert/delete at head or middle
- Size unknown at start
- Don't need random access by index

Avoid when:
- Need `arr[i]` — O(n) here vs O(1) array
- Cache performance matters (nodes scattered in memory)

---

## How — Build It

### Singly Linked List

```python
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None

class LinkedList:
    def __init__(self):
        self.head = None

    def prepend(self, data):          # O(1)
        node = Node(data)
        node.next = self.head
        self.head = node

    def append(self, data):           # O(n)
        node = Node(data)
        if not self.head:
            self.head = node
            return
        cur = self.head
        while cur.next:
            cur = cur.next
        cur.next = node

    def delete(self, data):           # O(n)
        if not self.head:
            return
        if self.head.data == data:
            self.head = self.head.next
            return
        cur = self.head
        while cur.next:
            if cur.next.data == data:
                cur.next = cur.next.next
                return
            cur = cur.next

    def search(self, data):           # O(n)
        cur = self.head
        while cur:
            if cur.data == data:
                return True
            cur = cur.next
        return False

    def to_list(self):                # O(n)
        result, cur = [], self.head
        while cur:
            result.append(cur.data)
            cur = cur.next
        return result
```

### Doubly Linked List

```python
class DNode:
    def __init__(self, data):
        self.data = data
        self.next = None
        self.prev = None

class DoublyLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None

    def append(self, data):           # O(1) — tail pointer
        node = DNode(data)
        if not self.tail:
            self.head = self.tail = node
            return
        node.prev = self.tail
        self.tail.next = node
        self.tail = node

    def delete(self, node):           # O(1) — given node reference
        if node.prev:
            node.prev.next = node.next
        else:
            self.head = node.next
        if node.next:
            node.next.prev = node.prev
        else:
            self.tail = node.prev
```

### Common Patterns

```python
# Reverse a linked list — O(n) time, O(1) space
def reverse(head):
    prev, cur = None, head
    while cur:
        nxt = cur.next
        cur.next = prev
        prev = cur
        cur = nxt
    return prev  # new head

# Detect cycle — Floyd's tortoise & hare
def has_cycle(head):
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
        if slow == fast:
            return True
    return False

# Find middle node
def find_middle(head):
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
    return slow  # slow is at middle
```

---

## Complexity

| Operation        | Singly | Doubly |
|------------------|--------|--------|
| Prepend          | O(1)   | O(1)   |
| Append           | O(n)*  | O(1)** |
| Delete by value  | O(n)   | O(n)   |
| Delete by node   | O(n)   | O(1)   |
| Search           | O(n)   | O(n)   |
| Access by index  | O(n)   | O(n)   |

\* O(1) if tail pointer maintained  
\*\* requires tail pointer

Space: O(n)

---

## Advantage / Disadvantage
| | |
|---|---|
| ✅ | O(1) insert/delete at head |
| ✅ | Dynamic size — no reallocation |
| ✅ | Doubly: O(1) delete given node reference |
| ❌ | No random access — O(n) to reach index i |
| ❌ | Extra memory for pointer(s) per node |
| ❌ | Poor cache locality (nodes scattered in heap) |

---

## Interview Questions
1. Reverse a linked list in-place → `O(n)` time, `O(1)` space — three-pointer swap
2. Detect cycle → Floyd's algorithm, two pointers at different speeds
3. Find middle of list → fast/slow pointer, fast moves 2x speed
4. Merge two sorted lists → compare heads, link smaller, recurse/iterate
5. Remove nth node from end → two pointers, gap of n between them
6. Intersection of two lists → advance longer list by length difference, then walk together

---

## Real World
| Use Case | Why Linked List |
|---|---|
| Browser history (back/forward) | Doubly — O(1) navigate prev/next |
| Undo/redo in text editor | Stack built on singly linked list |
| LRU Cache | Doubly + HashMap — O(1) move-to-front |
| OS process queue | Insertion order, dynamic size |
