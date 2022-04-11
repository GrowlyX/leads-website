package org.riverdell.website.tutorial

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
class Tutorial(
    // We want to use a string-based UUID
    val uniqueId: String,
    val author: String,
    var title: String,
    var image: String,
    var content: String
)