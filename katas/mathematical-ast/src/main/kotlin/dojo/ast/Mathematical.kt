// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

/** Construit l'arbre d'une expression RPN : les nombres s'empilent, un opérateur prend les deux derniers. */
object Mathematical {
    fun parse(rpn: String): Expression {
        val stack = ArrayDeque<Expression>()
        rpn.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }.forEach { token ->
            val operator = Operator.entries.firstOrNull { it.isWrittenAs(token) }
            if (operator == null) {
                stack.addLast(Operand(token.toLongOrNull() ?: throw IllegalArgumentException("Unknown token '$token'")))
            } else {
                require(stack.size >= 2) { "'$token' needs two operands" }
                val right = stack.removeLast()
                stack.addLast(Operation(operator, stack.removeLast(), right))
            }
        }
        require(stack.size == 1) { "Malformed expression '$rpn': ${stack.size} values left" }
        return stack.single()
    }
}
