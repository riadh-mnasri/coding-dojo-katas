// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

object Mathematical {
    fun parse(rpn: String): Expression {
        val stack = ArrayDeque<Expression>()
        rpn.trim().split(Regex("\\s+")).forEach { token ->
            val operator = Operator.entries.firstOrNull { it.isWrittenAs(token) }
            if (operator == null) {
                stack.addLast(Operand(token.toLong()))
            } else {
                val right = stack.removeLast()
                stack.addLast(Operation(operator, stack.removeLast(), right))
            }
        }
        return stack.single()
    }
}
