// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

class RpnCalculator {

    private val operators: Map<String, (Double, Double) -> Double> = mapOf(
        "+" to Double::plus,
        "-" to Double::minus,
        "*" to Double::times,
        "/" to Double::div,
    )

    fun evaluate(expression: String): Double {
        val stack = ArrayDeque<Double>()
        expression.split(" ").forEach { token ->
            val operator = operators[token]
            if (operator != null) {
                val right = stack.removeLast()
                val left = stack.removeLast()
                stack.addLast(operator(left, right))
            } else {
                stack.addLast(token.toDouble())
            }
        }
        return stack.single()
    }
}
