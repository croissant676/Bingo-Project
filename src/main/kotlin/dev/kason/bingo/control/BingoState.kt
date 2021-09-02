@file:Suppress("unused")

package dev.kason.bingo.control

enum class BingoState {
    LOADING,
    MENU,
    CREATION_MENU,
    SETTINGS,
    HOW_TO_USE;

    operator fun plus(state: Int): BingoState {
        if (state !in indices) {
            throw IndexOutOfBoundsException("Index out of bounds for companion to Bingo State.")
        }
        return BingoState[ordinal + state]
    }

    override fun toString(): String {
        return "BingoState{name=$name, ordinal=$ordinal}"
    }

    companion object : List<BingoState> {

        private val values = values()
        val indices: IntRange by lazy { values.indices }

        override operator fun get(index: Int): BingoState {
            if (index in indices) {
                return values[index]
            } else {
                throw IndexOutOfBoundsException("Index out of bounds for companion to Bingo State.")
            }
        }

        override val size: Int = values().size

        override fun contains(element: BingoState): Boolean {
            return true
        }

        override fun containsAll(elements: Collection<BingoState>): Boolean {
            if (elements.isEmpty()) {
                return false
            }
            return true
        }

        override fun indexOf(element: BingoState): Int {
            return element.ordinal
            /*
            for (index in indices) {
                if (element == values[index]) {
                    return index
                }
            }
            return -1
            */
        }

        override fun isEmpty(): Boolean {
            return false
        }

        override fun iterator(): Iterator<BingoState> {
            return object : Iterator<BingoState> {

                var currentIndex = 0

                override fun hasNext(): Boolean {
                    return currentIndex == values.lastIndex
                }

                override fun next(): BingoState {
                    return values[currentIndex++]
                }
            }
        }

        override fun lastIndexOf(element: BingoState): Int {
            return element.ordinal
            /*
            for (index in indices.reversed()) {
                if (element == values[index]) {
                    return index
                }
            }
            return -1
             */
        }

        override fun listIterator(): ListIterator<BingoState> {
            return object : ListIterator<BingoState> {

                var currentIndex = 0

                override fun hasNext(): Boolean {
                    return currentIndex < values.lastIndex
                }

                override fun next(): BingoState {
                    return values[currentIndex++]
                }

                override fun hasPrevious(): Boolean {
                    return currentIndex > 0
                }

                override fun nextIndex(): Int {
                    return if (hasNext()) {
                        currentIndex + 1
                    } else {
                        -1
                    }
                }

                override fun previous(): BingoState {
                    return values[currentIndex--]
                }

                override fun previousIndex(): Int {
                    return if (hasPrevious()) {
                        currentIndex - 1
                    } else {
                        -1
                    }
                }
            }
        }

        override fun listIterator(index: Int): ListIterator<BingoState> {
            // Same implementation, but starts at index
            return object : ListIterator<BingoState> {

                var currentIndex = index

                init {
                    if (currentIndex !in indices) {
                        throw IllegalArgumentException("Invalid index for list iterator: index=$index, valid=$indices")
                    }
                }

                override fun hasNext(): Boolean {
                    return currentIndex < values.lastIndex
                }

                override fun next(): BingoState {
                    return values[currentIndex++]
                }

                override fun hasPrevious(): Boolean {
                    return currentIndex > 0
                }

                override fun nextIndex(): Int {
                    return if (hasNext()) {
                        currentIndex + 1
                    } else {
                        -1
                    }
                }

                override fun previous(): BingoState {
                    return values[currentIndex--]
                }

                override fun previousIndex(): Int {
                    return if (hasPrevious()) {
                        currentIndex - 1
                    } else {
                        -1
                    }
                }
            }
        }

        override fun subList(fromIndex: Int, toIndex: Int): List<BingoState> {
            if (toIndex < fromIndex) {
                throw IllegalArgumentException("toIndex must be larger than fromIndex: fromIndex=$fromIndex, toIndex=$toIndex.")
            } else if (fromIndex !in indices) {
                throw IllegalArgumentException("fromIndex not in bounds for BingoState list: fromIndex=$fromIndex, indices=$indices")
            } else if (toIndex !in indices) {
                throw IllegalArgumentException("toIndex not in bounds for BingoState list: toIndex=$toIndex, indices=$indices")
            }
            return MutableList(toIndex - fromIndex) { newIndex ->
                values[fromIndex + newIndex]
            }
        }
    }
}

var indexOfCurrentState = 0

var currentState = BingoState.LOADING

private fun checkMatching() {
    if (BingoState[indexOfCurrentState] != currentState) {
        throw IllegalStateException("Index of current state does not match up to current state: currentState=$currentState, index=$indexOfCurrentState")
    }
}

fun moveToState(index: Int): BingoState {
    checkMatching()
    if (index !in BingoState.indices) {
        throw IndexOutOfBoundsException("Index: ${index}, indices: ${BingoState.indices}")
    }
    currentState = BingoState[index]
    indexOfCurrentState = index
    return currentState
}

fun moveToNextState(): BingoState {
    checkMatching()
    if (indexOfCurrentState + 1 !in BingoState.indices) {
        throw IndexOutOfBoundsException("Index: ${indexOfCurrentState + 1}, indices: ${BingoState.indices}")
    }
    indexOfCurrentState++
    currentState = BingoState[indexOfCurrentState]
    return currentState
}

fun moveToPreviousState(): BingoState {
    checkMatching()
    if (indexOfCurrentState - 1 !in BingoState.indices) {
        throw IndexOutOfBoundsException("Index: ${indexOfCurrentState - 1}, indices: ${BingoState.indices}")
    }
    indexOfCurrentState--
    currentState = BingoState[indexOfCurrentState]
    return currentState
}

fun moveBy(index: Int): BingoState {
    checkMatching()
    if (index !in BingoState.indices) {
        throw IllegalStateException("Index is not in Bingo state indices.")
    }
    indexOfCurrentState += index
    currentState = BingoState[indexOfCurrentState]
    return currentState
}