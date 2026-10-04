// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

/** Le Visiteur : chaque traitement de l'arbre (affichage, calcul...) est une classe à part, l'arbre ne change pas. */
interface Visitor<R> {
    fun visit(operand: Operand): R
    fun visit(operation: Operation): R
}

sealed interface Expression {
    fun <R> accept(visitor: Visitor<R>): R
}

data class Operand(val value: Long) : Expression {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
}

enum class Operator(val symbol: String, private vararg val aliases: String) {
    ADD("+"),
    MULTIPLY("×", "*"),
    ;

    fun isWrittenAs(token: String) = token == symbol || token in aliases
}

data class Operation(val operator: Operator, val left: Expression, val right: Expression) : Expression {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
}
