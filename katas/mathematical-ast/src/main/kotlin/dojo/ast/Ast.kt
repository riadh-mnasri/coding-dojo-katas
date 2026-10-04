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

/** Comment se regroupent des opérations de même priorité écrites à la suite. */
enum class Grouping {
    /** (a op b) op c = a op (b op c) : jamais de parenthèses à priorité égale. */
    ANY,

    /** a - b - c se lit (a - b) - c : il en faut à droite. */
    LEFT,

    /** a ^ b ^ c se lit a ^ (b ^ c) : il en faut à gauche. */
    RIGHT,
}

enum class Operator(val symbol: String, val precedence: Int, val grouping: Grouping, private vararg val aliases: String) {
    ADD("+", 1, Grouping.ANY),
    SUBTRACT("-", 1, Grouping.LEFT),
    MULTIPLY("×", 2, Grouping.ANY, "*"),
    POWER("^", 3, Grouping.RIGHT),
    ;

    fun isWrittenAs(token: String) = token == symbol || token in aliases
}

data class Operation(val operator: Operator, val left: Expression, val right: Expression) : Expression {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visit(this)
}
