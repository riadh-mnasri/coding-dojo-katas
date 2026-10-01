// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

import kotlin.math.sqrt

/** Une opération consomme le haut de la pile et y dépose son résultat. */
fun interface Operation {
    fun applyTo(stack: ArrayDeque<Double>)
}

class RpnCalculator {

    private val operations: Map<String, Operation> = mapOf(
        "+" to binary(Double::plus),
        "-" to binary(Double::minus),
        "*" to binary(Double::times),
        "/" to binary(Double::div),
        "SQRT" to unary(::sqrt),
    )

    fun evaluate(expression: String): Double {
        val stack = ArrayDeque<Double>()
        expression.split(" ").forEach { token ->
            operations[token]?.applyTo(stack) ?: stack.addLast(token.toDouble())
        }
        return stack.single()
    }

    private fun binary(compute: (Double, Double) -> Double) = Operation { stack ->
        val right = stack.removeLast()
        val left = stack.removeLast()
        stack.addLast(compute(left, right))
    }

    private fun unary(compute: (Double) -> Double) = Operation { stack ->
        stack.addLast(compute(stack.removeLast()))
    }
}
