package ru.itmo.software_design.lrucache

import java.util.*
import kotlin.collections.HashMap

class LRUCache<K, V>(private val capacity: Int) {
    private val data: HashMap<K, V> = HashMap(capacity)
    private val order: LinkedList<K> = LinkedList()

    init {
        require(capacity > 0) { "Capacity must be greater than 0" }
    }

    fun get(key: K): V? {
        invariant()
        if (data.containsKey(key)) {
            order.remove(key)
            order.addFirst(key)
            invariant()
            assert(order.first == key) { "Order is not correct" }
            return data[key]
        }
        invariant()
        return null
    }

    fun put(key: K, value: V) {
        invariant()
        if (data.containsKey(key)) {
            order.remove(key)
        } else if (data.size == capacity) {
            val last = order.removeLast()
            data.remove(last)
        }
        order.addFirst(key)
        data[key] = value
        assert(order.first == key) { "Order is not correct" }
        assert(data[key] == value) { "Data has not been saved" }
        invariant()
    }

    private fun invariant() {
        assert(data.size <= capacity) { "Data size must be less than or equal to capacity" }
        assert(data.size == order.size) { "Data and order sizes must be equal" }
        assert(data.keys == order.toSet()) { "Data and order must contain the same keys" }
    }
}