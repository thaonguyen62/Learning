# Complexity Analysis

## What
Measuring how resource usage (time or memory) grows as input size `n` grows.
Two dimensions: **Time Complexity** and **Space Complexity**.

---

## Time Complexity

### What
Count of operations an algorithm performs relative to input size `n`.

### Why
Predict performance before running code. Compare algorithms objectively.

### When to Use
- Choosing between two algorithms solving same problem
- Predicting if solution will TLE (Time Limit Exceeded) in interviews
- Estimating production scalability

### How — Big-O Notation
Describes **upper bound** (worst case) growth rate. Ignore constants and lower-order terms.

```
O(1)        → Constant    — hash lookup
O(log n)    → Logarithmic — binary search
O(n)        → Linear      — single loop
O(n log n)  → Linearithmic— merge sort
O(n²)       → Quadratic   — nested loop
O(2ⁿ)       → Exponential — all subsets
O(n!)       → Factorial   — all permutations
```

**Growth order (fast → slow):**
```
O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2ⁿ) < O(n!)
```

### How to Calculate
1. Identify loops → each loop multiplies complexity
2. Drop constants: `O(3n)` → `O(n)`
3. Drop lower terms: `O(n² + n)` → `O(n²)`
4. Nested loops → multiply: O(n) × O(n) = O(n²)
5. Sequential loops → add then simplify: O(n) + O(n) = O(n)

```python
# O(1) — no growth
def get_first(arr):
    return arr[0]

# O(n) — one loop
def find_max(arr):
    max_val = arr[0]
    for x in arr:        # n iterations
        if x > max_val:
            max_val = x
    return max_val

# O(n²) — nested loop
def bubble_sort(arr):
    for i in range(len(arr)):        # n
        for j in range(len(arr)-1):  # n
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]

# O(log n) — halving each step
def binary_search(arr, target):
    lo, hi = 0, len(arr) - 1
    while lo <= hi:
        mid = (lo + hi) // 2
        if arr[mid] == target: return mid
        elif arr[mid] < target: lo = mid + 1
        else: hi = mid - 1
    return -1
```

### Advantage / Disadvantage
| | |
|---|---|
| ✅ | Language/hardware-agnostic comparison |
| ✅ | Predicts scaling behavior |
| ❌ | Ignores constants (O(n) with large constant can beat O(log n) at small n) |
| ❌ | Worst-case may rarely occur |

### Big-O vs Big-Ω vs Big-Θ
| Notation | Meaning | Usage |
|---|---|---|
| O (Big-O) | Upper bound (worst case) | Most common in interviews |
| Ω (Big-Omega) | Lower bound (best case) | Rarely asked |
| Θ (Big-Theta) | Tight bound (average) | Precise analysis |

### Interview Questions
1. What is time complexity of binary search? → `O(log n)`
2. Nested loop over n elements — complexity? → `O(n²)`
3. Two separate loops over n — complexity? → `O(n)`
4. Difference between O(n) and O(log n)? → Linear vs halving each step
5. When is O(n²) acceptable? → Small n (< 10,000), or no better algorithm exists

### Real World
| Scenario | Complexity |
|---|---|
| Google search autocomplete | O(log n) — trie lookup |
| Sorting emails by date | O(n log n) — merge/quick sort |
| Finding friend of friend | O(V+E) — graph BFS |
| Checking if username taken | O(1) — hash set |

---

## Space Complexity

### What
Amount of memory an algorithm uses relative to input size `n`.
Includes: variables, call stack (recursion), auxiliary data structures.

### Why
Memory is finite. Space complexity catches stack overflow risks and memory bloat.

### When to Care
- Embedded/mobile systems (tight memory)
- Recursive algorithms (stack depth = space)
- Interviewer asks for "O(1) space" solution

### How to Calculate
Count extra memory allocated (not counting input itself = auxiliary space).

```python
# O(1) space — only a few variables
def sum_array(arr):
    total = 0          # 1 variable
    for x in arr:
        total += x
    return total

# O(n) space — new array
def double_array(arr):
    return [x * 2 for x in arr]  # n elements allocated

# O(n) space — recursion stack
def factorial(n):
    if n == 1: return 1
    return n * factorial(n - 1)  # n frames on call stack

# O(log n) space — binary search recursive
def bin_search(arr, lo, hi, target):
    if lo > hi: return -1
    mid = (lo + hi) // 2
    if arr[mid] == target: return mid
    elif arr[mid] < target: return bin_search(arr, mid+1, hi, target)
    else: return bin_search(arr, lo, mid-1, target)
    # log n recursive calls on stack
```

### Time-Space Tradeoff
Classic tradeoff: use more memory to save time.

```
Example: check duplicates
- Naive: O(n²) time, O(1) space  → nested loop comparison
- Smart: O(n) time, O(n) space   → hash set
```

### Advantage / Disadvantage
| | |
|---|---|
| ✅ | Prevents OOM errors in production |
| ✅ | Forces thinking about in-place algorithms |
| ❌ | Hard to measure exactly (OS memory management varies) |
| ❌ | Trade-off with time — optimizing one often hurts other |

### Interview Questions
1. What space does recursive fibonacci use? → `O(n)` (call stack depth)
2. How to reverse array in-place? → Two pointers, O(1) space
3. What is auxiliary space? → Extra space excluding input
4. BFS vs DFS space complexity on tree? → BFS O(w) where w=max width, DFS O(h) where h=height
5. Can you solve X in O(1) space? → Usually means in-place, no extra data structures

### Real World
| Scenario | Space concern |
|---|---|
| Mobile app caching | O(n) cache trades space for speed |
| Deep recursion (parser) | Stack overflow at O(n) depth → convert to iterative |
| Streaming large files | O(1) space — process chunk by chunk |
| DP memoization | O(n) or O(n²) to avoid recomputation |

---

## Quick Reference Cheatsheet

```
Operation          | Array | LinkedList | Stack | Queue | HashMap | BST    | Heap
-------------------|-------|------------|-------|-------|---------|--------|-------
Access by index    | O(1)  | O(n)       | O(n)  | O(n)  | O(1)    | O(log n)| O(n)
Search             | O(n)  | O(n)       | O(n)  | O(n)  | O(1)    | O(log n)| O(n)
Insert (beginning) | O(n)  | O(1)       | —     | —     | O(1)    | O(log n)| —
Insert (end)       | O(1)* | O(n)       | O(1)  | O(1)  | O(1)    | O(log n)| O(log n)
Delete             | O(n)  | O(1)**     | O(1)  | O(1)  | O(1)    | O(log n)| O(log n)
Min/Max            | O(n)  | O(n)       | O(n)  | O(n)  | O(n)    | O(log n)| O(1)

* amortized (dynamic array)
** given pointer to node
```
