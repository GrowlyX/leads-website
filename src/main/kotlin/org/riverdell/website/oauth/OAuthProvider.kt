package org.riverdell.website.oauth

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
abstract class OAuthProvider
{
    abstract fun getIdentifier(): String
    abstract fun getIcon(): String

    abstract fun getRedirect(): String
}
