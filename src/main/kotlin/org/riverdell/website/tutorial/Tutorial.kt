package org.riverdell.website.tutorial

import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
class Tutorial(
    // We want to use a string-based UUID
    val uniqueId: String,
    val author: String,
    var title: String,
    var subTitle: String,
    var image: String,
    var content: String,
    var description: String,
    var labels: List<String>
)
{
    var created by Delegates.notNull<Long>()
}