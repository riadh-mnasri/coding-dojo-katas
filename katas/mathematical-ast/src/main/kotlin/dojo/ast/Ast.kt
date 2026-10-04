// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

sealed interface Expression

data class Operand(val value: Long) : Expression

enum class Operator(val symbol: String, private vararg val aliases: String) {
    ADD("+"),
    MULTIPLY("×", "*"),
    ;

    fun isWrittenAs(token: String) = token == symbol || token in aliases
}

data class Operation(val operator: Operator, val left: Expression, val right: Expression) : Expression
