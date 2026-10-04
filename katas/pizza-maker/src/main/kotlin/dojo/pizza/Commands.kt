// Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
package dojo.pizza

/** La couche interactive : traduit une phrase en appel au four, et sa réponse en phrase. */
class Commands(private val oven: Oven) {
    private val cook = Regex("""cook an? (.+?) pizza""", RegexOption.IGNORE_CASE)

    fun execute(command: String): String {
        val recipe = cook.matchEntire(command.trim())?.groupValues?.get(1)
        return when {
            recipe != null -> {
                oven.cook(recipe)
                "$recipe goes in the oven."
            }
            command.equals("show queue", ignoreCase = true) ->
                oven.queue().joinToString("\n") { "${it.recipe}: ${it.state.name.lowercase()}" }
            command.equals("get out the pizza", ignoreCase = true) -> {
                val pizza = oven.takeOut()
                "${pizza.recipe} is out, ${pizza.state.name.lowercase()}. Points: ${oven.points}"
            }
            else -> "Unknown command '$command'"
        }
    }
}
