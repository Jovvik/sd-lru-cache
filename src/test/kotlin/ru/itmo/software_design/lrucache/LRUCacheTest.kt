package ru.itmo.software_design.lrucache

import kotlin.test.Test
import kotlin.test.assertFailsWith

class LRUCacheTest {
    @Test
    fun `cache is empty when created`() {
        val cache = LRUCache<Int, Int>(8)
        assert(cache.get(1) == null)
    }

    @Test
    fun `get returns null when key is not present`() {
        val cache = LRUCache<Int, Int>(1)
        assert(cache.get(1) == null)
    }

    @Test
    fun `get returns the value when key is present`() {
        val cache = LRUCache<Int, Int>(1)
        cache.put(1, 2)
        assert(cache.get(1) == 2)
    }

    @Test
    fun `cache forgets the least recently put item`() {
        val cache = LRUCache<Int, Int>(2)
        cache.put(1, 4)
        cache.put(2, 5)
        cache.put(3, 6)
        assert(cache.get(1) == null)
        assert(cache.get(2) == 5)
        assert(cache.get(3) == 6)
    }

    @Test
    fun `cache forgets the least recently obtained item`() {
        val cache = LRUCache<Int, Int>(2)
        cache.put(1, 4)
        cache.put(2, 5)
        cache.get(1)
        cache.put(3, 6)
        assert(cache.get(1) == 4)
        assert(cache.get(2) == null)
        assert(cache.get(3) == 6)
    }

    @Test
    fun `cache overwrites the value when key is already present`() {
        val cache = LRUCache<Int, Int>(1)
        cache.put(1, 4)
        cache.put(1, 5)
        assert(cache.get(1) == 5)
    }

    @Test
    fun `cache does not accept incorrect capacity`() {
        assertFailsWith<IllegalArgumentException> {
            LRUCache<Int, Int>(0)
        }
        assertFailsWith<IllegalArgumentException> {
            LRUCache<Int, Int>(-1)
        }
    }
}