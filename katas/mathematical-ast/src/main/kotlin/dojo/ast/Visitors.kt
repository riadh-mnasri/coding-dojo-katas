// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

object RpnPrinter : Visitor<String> {
    override fun visit(operand: Operand) = operand.value.toString()

    override fun visit(operation: Operation) =
        "${operation.left.accept(this)} ${operation.right.accept(this)} ${operation.operator.symbol}"
}

object Evaluator : Visitor<Long> {
    fun power(base: Long, exponent: Long): Long = (1..exponent).fold(1L) { result, _ -> result * base }

    /** La notation de Knuth : a ↑ b = a^b, et a ↑ⁿ b = a ↑ⁿ⁻¹ (a ↑ⁿ b-1), avec a ↑ⁿ 0 = 1. */
    private fun arrows(base: Long, count: Int, operand: Long): Long = when {
        count == 1 -> power(base, operand)
        operand == 0L -> 1L
        else -> arrows(base, count - 1, arrows(base, count, operand - 1))
    }

    override fun visit(operand: Operand) = operand.value

    override fun visit(operation: Operation): Long {
        val left = operation.left.accept(this)
        val right = operation.right.accept(this)
        return when (operation.operator) {
            Operator.ADD -> left + right
            Operator.SUBTRACT -> left - right
            Operator.MULTIPLY -> left * right
            Operator.POWER -> power(left, right)
            Operator.UP_ARROW -> arrows(left, 1, right)
            Operator.DOUBLE_UP_ARROW -> arrows(left, 2, right)
        }
    }
}

/** Infixe : chaque opération imbriquée est mise entre parenthèses. */
object InfixPrinter : Visitor<String> {
    override fun visit(operand: Operand) = operand.value.toString()

    override fun visit(operation: Operation) =
        "${nested(operation.left)} ${operation.operator.symbol} ${nested(operation.right)}"

    private fun nested(expression: Expression) =
        if (expression is Operation) "(${expression.accept(this)})" else expression.accept(this)
}

/** Étape 3 : une parenthèse seulement quand la priorité des opérateurs l'exige. */
object MinimalInfixPrinter : Visitor<String> {
    override fun visit(operand: Operand) = operand.value.toString()

    override fun visit(operation: Operation): String {
        val precedence = operation.operator.precedence
        val grouping = operation.operator.grouping
        val left = if (grouping == Grouping.RIGHT) precedence + 1 else precedence
        val right = if (grouping == Grouping.LEFT) precedence + 1 else precedence
        return "${wrapped(operation.left, left)} ${operation.operator.symbol} ${wrapped(operation.right, right)}"
    }

    /** Parenthèses si l'enfant lie moins fort que [required]. */
    private fun wrapped(child: Expression, required: Int): String {
        val text = child.accept(this)
        return if (child is Operation && child.operator.precedence < required) "($text)" else text
    }
}
