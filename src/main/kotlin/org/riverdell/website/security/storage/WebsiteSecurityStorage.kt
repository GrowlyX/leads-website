package org.riverdell.website.security.storage

import io.supabase.gotrue.AuthStorage

/**
 * @author GrowlyX
 * @since 4/2/2022
 */
object WebsiteSecurityStorage : AuthStorage
{
    override fun get(key: String): String?
    {
        TODO("Not yet implemented")
    }

    override fun remove(key: String)
    {
        TODO("Not yet implemented")
    }

    override fun set(key: String, value: String)
    {
        TODO("Not yet implemented")
    }
}
