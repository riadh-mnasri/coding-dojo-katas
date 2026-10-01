// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

class RpnCalculator {
    fun evaluate(expression: String): Double {
        val stack = ArrayDeque<Double>()
        expression.split(" ").forEach { token ->
            when (token) {
                "+" -> stack.addLast(stack.removeLast() + stack.removeLast())
                "-" -> {
                    val right = stack.removeLast()
                    stack.addLast(stack.removeLast() - right)
                }
                "*" -> stack.addLast(stack.removeLast() * stack.removeLast())
                "/" -> {
                    val right = stack.removeLast()
                    stack.addLast(stack.removeLast() / right)
                }
                else -> stack.addLast(token.toDouble())
            }
        }
        return stack.single()
    }
}
