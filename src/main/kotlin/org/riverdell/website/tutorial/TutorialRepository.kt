package org.riverdell.website.tutorial

import org.riverdell.website.repository.Repositories

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
object TutorialRepository
{
    private val repository = Repositories
        .store<Tutorial>("uniqueId")

    fun repository() = repository
}