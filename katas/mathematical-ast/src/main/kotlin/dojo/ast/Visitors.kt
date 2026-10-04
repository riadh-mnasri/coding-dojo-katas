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
