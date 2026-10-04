// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

object RpnPrinter : Visitor<String> {
    override fun visit(operand: Operand) = operand.value.toString()

    override fun visit(operation: Operation) =
        "${operation.left.accept(this)} ${operation.right.accept(this)} ${operation.operator.symbol}"
}

object Evaluator : Visitor<Long> {
    override fun visit(operand: Operand) = operand.value

    override fun visit(operation: Operation): Long {
        val left = operation.left.accept(this)
        val right = operation.right.accept(this)
        return when (operation.operator) {
            Operator.ADD -> left + right
            Operator.MULTIPLY -> left * right
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
