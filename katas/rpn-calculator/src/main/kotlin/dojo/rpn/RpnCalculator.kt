// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.rpn

class RpnCalculator {
    fun evaluate(expression: String): Double {
        val stack = ArrayDeque<Double>()
        expression.split(" ").forEach { token ->
            if (token == "+") {
                stack.addLast(stack.removeLast() + stack.removeLast())
            } else {
                stack.addLast(token.toDouble())
            }
        }
        return stack.single()
    }
}
