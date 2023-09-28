package com.adammcneilly.toa

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Hopefully this error gets fixed soon, but basically it happens when you have generic receivers that "might", but don't necessarily, have a subtyping relation
 *
 * https://discuss.kotlinlang.org/t/using-with-function-with-multiple-receivers/2062/7
 */
@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, R> withAll(a: A, b: B, block: context(A, B) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b)
}

@OptIn(ExperimentalContracts::class)
@Suppress("SUBTYPING_BETWEEN_CONTEXT_RECEIVERS")
inline fun <A, B, C, R> withAll(a: A, b: B, c: C, block: context(A, B, C) () -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(a, b, c)
}
