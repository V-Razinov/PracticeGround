package ru.practiceground.other

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class QueuedRunner {
    private val mutex = Mutex()

    suspend fun <T> runAfter(action: () -> T) = mutex.withLock { action() }
}