package ru.itmo.software_design.lrucache

import kotlin.collections.HashMap

class LRUCache<K, V>(private val capacity: Int) {
    private val data: HashMap<K, DoublyLinkedList<Pair<K, V>>.Node> = HashMap(capacity)
    private val order: DoublyLinkedList<Pair<K, V>> = DoublyLinkedList()

    init {
        require(capacity > 0) { "Capacity must be greater than 0" }
    }

    fun get(key: K): V? {
        return keepInvariant {
            data[key]?.let { node ->
                order.remove(node)
                data[key] = order.addFirst(node.value)
                assert(order.first()?.value?.first == key)
                node.value.second
            }
        }
    }

    fun put(key: K, value: V) {
        return keepInvariant {
            data[key]?.let { node ->
                order.remove(node)
            } ?: run {
                // impossible if we just removed the node, hence the ?:
                if (order.size() == capacity) {
                    val last = order.removeLast()
                    data.remove(last.first)
                }
            }
            data[key] = order.addFirst(key to value)
            assert(order.first()?.value?.first == key)
            assert(order.first()?.value?.second == value)
        }
    }

    private fun invariant() {
        assert(data.size <= capacity) { "Data size must be less than or equal to capacity" }
        assert(data.size == order.size()) { "Data and order sizes must be equal" }
    }

    // Kotlin doesn't have self-types, so this can't be abstracted away
    private inline fun <R> keepInvariant(fn: LRUCache<K, V>.() -> R): R {
        return invariant().let { fn() }.also { invariant() }
    }
}