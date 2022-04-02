package org.riverdell.website.model

import java.util.UUID

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
class WebsiteUser(
    val uniqueId: UUID,
    val email: String,
    val username: String
)
