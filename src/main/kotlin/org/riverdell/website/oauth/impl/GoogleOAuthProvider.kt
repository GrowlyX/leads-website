package org.riverdell.website.oauth.impl

import org.riverdell.website.oauth.OAuthProvider

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
object GoogleOAuthProvider : OAuthProvider()
{
    override fun getIdentifier() = "google"
    override fun getIcon() = "todo"

    override fun getRedirect(): String
    {
        TODO("Not yet implemented")
    }
}
