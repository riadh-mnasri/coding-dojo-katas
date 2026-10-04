// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

sealed interface Expression

data class Operand(val value: Long) : Expression
