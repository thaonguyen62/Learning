# Stack

## What
**LIFO** — Last In, First Out.
Push to top, pop from top. Only top element accessible.

```
push(3) → [1, 2, 3]  ← top
pop()   → [1, 2]     returns 3
peek()  → 2          (no remove)
```

---

## When to Use
- Reverse something (reverse order of push = original order)
- Track previous state (undo, backtrack)
- Nested structures (parentheses, function calls, HTML tags)
- DFS traversal (iterative)

---

## How — Build It

### Using Python List (simplest)

```python
stack = []
stack.append(1)    # push — O(1)
stack.append(2)
stack.append(3)
stack.pop()        # pop  — O(1) → returns 3
stack[-1]          # peek — O(1) → returns 2
len(stack) == 0    # empty check
```

### Custom Stack (interview-safe)

```python
class Stack:
    def __init__(self):
        self._data = []

    def push(self, val):         # O(1)
        self._data.append(val)

    def pop(self):               # O(1)
        if self.is_empty():
            raise IndexError("pop from empty stack")
        return self._data.pop()

    def peek(self):              # O(1)
        if self.is_empty():
            raise IndexError("peek at empty stack")
        return self._data[-1]

    def is_empty(self):
        return len(self._data) == 0

    def size(self):
        return len(self._data)
```

### Stack with Linked List (O(1) guaranteed, no amortized)

```python
class Node:
    def __init__(self, val):
        self.val = val
        self.next = None

class LinkedStack:
    def __init__(self):
        self.top = None
        self._size = 0

    def push(self, val):         # O(1)
        node = Node(val)
        node.next = self.top
        self.top = node
        self._size += 1

    def pop(self):               # O(1)
        if not self.top:
            raise IndexError("pop from empty stack")
        val = self.top.val
        self.top = self.top.next
        self._size -= 1
        return val

    def peek(self):              # O(1)
        return self.top.val if self.top else None
```

### Common Patterns

```python
# Valid parentheses — classic stack problem
def is_valid(s: str) -> bool:
    stack = []
    pairs = {')': '(', '}': '{', ']': '['}
    for c in s:
        if c in '({[':
            stack.append(c)
        elif c in pairs:
            if not stack or stack[-1] != pairs[c]:
                return False
            stack.pop()
    return len(stack) == 0

# Evaluate postfix expression (2 3 + 4 * = 20)
def eval_postfix(tokens):
    stack = []
    ops = {'+': lambda a,b: a+b, '-': lambda a,b: a-b,
           '*': lambda a,b: a*b, '/': lambda a,b: int(a/b)}
    for t in tokens:
        if t in ops:
            b, a = stack.pop(), stack.pop()
            stack.append(ops[t](a, b))
        else:
            stack.append(int(t))
    return stack[0]

# Min stack — O(1) getMin
class MinStack:
    def __init__(self):
        self.stack = []
        self.min_stack = []  # parallel stack tracking minimums

    def push(self, val):
        self.stack.append(val)
        min_val = min(val, self.min_stack[-1] if self.min_stack else val)
        self.min_stack.append(min_val)

    def pop(self):
        self.min_stack.pop()
        return self.stack.pop()

    def get_min(self):       # O(1)
        return self.min_stack[-1]

# Iterative DFS using stack
def dfs_iterative(graph, start):
    visited = set()
    stack = [start]
    while stack:
        node = stack.pop()
        if node not in visited:
            visited.add(node)
            for neighbor in graph[node]:
                stack.append(neighbor)
    return visited
```

---

## Complexity

| Operation | Time |
|-----------|------|
| Push      | O(1) |
| Pop       | O(1) |
| Peek      | O(1) |
| Search    | O(n) |

Space: O(n)

---

## Advantage / Disadvantage
| | |
|---|---|
| ✅ | O(1) push/pop/peek |
| ✅ | Simple — models call stack, recursion, undo |
| ✅ | Converts recursive problems to iterative |
| ❌ | Only top accessible — no random access |
| ❌ | No search — O(n) scan required |

---

## Interview Questions
1. Valid parentheses → push open brackets, match on close
2. Min stack with O(1) getMin → parallel min-tracking stack
3. Implement queue using two stacks → stack1 for push, stack2 for pop (lazy transfer)
4. Evaluate postfix expression → push numbers, pop two on operator
5. Next greater element → monotonic stack, maintain decreasing order
6. Largest rectangle in histogram → monotonic stack tracking indices

---

## Real World
| Use Case | Why Stack |
|---|---|
| Function call stack (CPU) | Each call frame pushed; return pops |
| Browser forward/back | Back = pop from history stack |
| Undo in editors | Each edit pushed; ctrl+z pops |
| Compiler: expression parsing | Shunting-yard algorithm |
| DFS (iterative) | Explicit stack replaces recursion |
