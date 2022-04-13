package org.riverdell.website.loader

/**
 * @author GrowlyX
 * @since 4/13/2022
 */
interface CompositeLoader<T>
{
    fun load(t: T)
}
