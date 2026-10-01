// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

import kotlin.math.sqrt

/**
 * La pile de l'évaluation. Elle retient où s'arrête le résultat de la dernière opération,
 * pour les opérations comme MAX qui portent sur « tous les opérandes qui suivent ».
 */
class OperandStack {
    private val values = ArrayDeque<Double>()
    private var lastResultIndex = 0

    fun push(value: Double) = values.addLast(value)

    fun pop(): Double {
        require(values.isNotEmpty()) { "Missing operand" }
        return values.removeLast()
    }

    fun popOperandsSinceLastOperation(): List<Double> {
        require(values.size > lastResultIndex) { "Missing operand" }
        return List(values.size - lastResultIndex) { values.removeLast() }.reversed()
    }

    fun pushResult(value: Double) {
        push(value)
        lastResultIndex = values.size
    }

    fun single(): Double {
        require(values.size == 1) { "Malformed expression: ${values.size} values left on the stack" }
        return values.single()
    }
}

/** Une opération consomme des valeurs de la pile et y dépose son résultat. */
fun interface Operation {
    fun applyTo(stack: OperandStack)
}

class RpnCalculator(private val operations: Map<String, Operation> = defaultOperations) {

    fun evaluate(expression: String): Double {
        val stack = OperandStack()
        expression.split(" ").forEach { token ->
            operations[token]?.applyTo(stack)
                ?: stack.push(token.toDoubleOrNull() ?: throw IllegalArgumentException("Unknown token '$token'"))
        }
        return stack.single()
    }

    companion object {
        private fun binary(compute: (Double, Double) -> Double) = Operation { stack ->
            val right = stack.pop()
            val left = stack.pop()
            stack.pushResult(compute(left, right))
        }

        private fun unary(compute: (Double) -> Double) = Operation { stack ->
            stack.pushResult(compute(stack.pop()))
        }

        val defaultOperations: Map<String, Operation> = mapOf(
            "+" to binary(Double::plus),
            "-" to binary(Double::minus),
            "*" to binary(Double::times),
            "/" to binary { left, right ->
                require(right != 0.0) { "Division by zero" }
                left / right
            },
            "SQRT" to unary(::sqrt),
            "MAX" to Operation { stack -> stack.pushResult(stack.popOperandsSinceLastOperation().max()) },
        )
    }
}
