package org.riverdell.website.tutorial

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
class Tutorial(
    // We want to use a string-based UUID
    val uniqueId: String,
    val author: String
)
{
    var title = ""
    var subTitle = ""
    var image = ""
    var content = ""
    var description = ""
    var labels = ""

    var created = 0L
}
