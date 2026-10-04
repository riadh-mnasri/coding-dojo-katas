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
            Operator.SUBTRACT -> left - right
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

/** Étape 3 : une parenthèse seulement quand la priorité des opérateurs l'exige. */
object MinimalInfixPrinter : Visitor<String> {
    override fun visit(operand: Operand) = operand.value.toString()

    override fun visit(operation: Operation): String {
        val precedence = operation.operator.precedence
        val right = if (operation.operator.associative) precedence else precedence + 1
        return "${wrapped(operation.left, precedence)} ${operation.operator.symbol} ${wrapped(operation.right, right)}"
    }

    /** Parenthèses si l'enfant lie moins fort que [required]. */
    private fun wrapped(child: Expression, required: Int): String {
        val text = child.accept(this)
        return if (child is Operation && child.operator.precedence < required) "($text)" else text
    }
}
