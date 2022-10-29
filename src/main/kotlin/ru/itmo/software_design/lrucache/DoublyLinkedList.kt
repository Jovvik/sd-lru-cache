package ru.itmo.software_design.lrucache

class DoublyLinkedList<T> : Iterable<DoublyLinkedList<T>.Node> {
    private var head: Node? = null
    private var tail: Node? = null
    private var size = 0

    fun addFirst(value: T): Node {
        return keepInvariant {
            val node = Node(value)
            if (head == null) {
                head = node
                tail = node
            } else {
                node.next = head
                head!!.prev = node
                head = node
            }
            size++
            node
        }.also {
            check(it.value == value)
            check(it in this)
        }
    }

    fun removeLast(): T {
        checkNotNull(tail) { "List is empty" }
        return keepInvariant {
            val value = tail!!.value
            if (tail!!.prev == null) {
                head = null
                tail = null
            } else {
                tail = tail!!.prev
                tail!!.next = null
            }
            size--
            value
        }
    }

    fun remove(node: Node) {
        check(node in this) { "Node is not in the list" }
        keepInvariant {
            if (node.prev == null) {
                head = node.next
            } else {
                node.prev!!.next = node.next
            }
            if (node.next == null) {
                tail = node.prev
            } else {
                node.next!!.prev = node.prev
            }
            size--
        }
        check(node !in this) { "Node is still in the list" }
    }

    fun size(): Int = size

    fun first(): Node? = head

    fun last(): Node? = tail

    inner class Node(val value: T) {
        var next: Node? = null
        var prev: Node? = null
    }

    override fun iterator(): Iterator<Node> = object : Iterator<Node> {
        var current = head

        override fun hasNext(): Boolean = current != null

        override fun next(): DoublyLinkedList<T>.Node {
            val node = current!!
            current = current!!.next
            return node
        }
    }

    private fun invariant() {
        assert(size >= 0) { "Size must be non-negative" }
        if (size != 0) {
            assert(head != null) { "Head must not be null if size is not 0" }
            assert(tail != null) { "Tail must not be null if size is not 0" }
        }
        assert(head?.prev == null) { "Head must not have a previous node" }
        assert(tail?.next == null) { "Tail must not have a next node" }
        if (size == 1) {
            assert(head == tail) { "Head and tail must be equal if size is 1" }
        }
        if (size == 2) {
            assert(head?.next == tail) { "Head must point to tail if size is 2" }
            assert(tail?.prev == head) { "Tail must point to head if size is 2" }
        }
        assert(iterator().asSequence().toList().size == size) { "Iterator size must be equal to the real size" }
    }

    private inline fun <R> keepInvariant(fn: DoublyLinkedList<T>.() -> R): R {
        return invariant().let { fn() }.also { invariant() }
    }
}