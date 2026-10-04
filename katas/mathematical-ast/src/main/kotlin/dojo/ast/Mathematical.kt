// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.ast

object Mathematical {
    fun parse(rpn: String): Expression = Operand(rpn.trim().toLong())
}
