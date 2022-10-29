package ru.itmo.software_design.lrucache

import kotlin.test.Test

class DoublyLinkedListTest {
    @Test
    fun `addFirst adds a node to the beginning of the list`() {
        val list = DoublyLinkedList<Int>()
        val node = list.addFirst(1)
        assert(list.first() == node)
        assert(list.last() == node)
        assert(list.size() == 1)
    }

    @Test
    fun `addFirst adds a node to the beginning of the list when the list is not empty`() {
        val list = DoublyLinkedList<Int>()
        val node1 = list.addFirst(1)
        val node2 = list.addFirst(2)
        assert(list.first() == node2)
        assert(list.last() == node1)
        assert(list.size() == 2)
    }

    @Test
    fun `removeLast removes the last node from the list`() {
        val list = DoublyLinkedList<Int>()
        val node = list.addFirst(1)
        assert(list.removeLast() == 1)
        assert(list.size() == 0)
        assert(node !in list)
    }

    @Test
    fun `list is empty when created`() {
        val list = DoublyLinkedList<Int>()
        assert(list.size() == 0)
        assert(list.first() == null)
        assert(list.last() == null)
    }

    @Test
    fun `removeLast removes the last node from the list when the list is not empty`() {
        val list = DoublyLinkedList<Int>()
        val node1 = list.addFirst(1)
        val node2 = list.addFirst(2)
        assert(list.removeLast() == 1)
        assert(list.size() == 1)
        assert(node1 !in list)
        assert(node2 in list)
    }
}