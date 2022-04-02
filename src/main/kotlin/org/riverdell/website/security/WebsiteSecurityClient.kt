package org.riverdell.website.security

import io.supabase.gotrue.GoTrueClient
import io.supabase.gotrue.MemoryStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * @author GrowlyX
 * @since 4/2/2022
 */
@Configuration
open class WebsiteSecurityClient
{
    @Value("\${supabase.authorization}")
    lateinit var authorization: String

    @Value("\${supabase.project-id}")
    lateinit var projectId: String

    val client = GoTrueClient(
        url = "https://$projectId.supabase.co/auth/v1",
        authorization = authorization,
        // TODO: 4/2/2022 use mongo storage
        storage = MemoryStorage()
    )
}
