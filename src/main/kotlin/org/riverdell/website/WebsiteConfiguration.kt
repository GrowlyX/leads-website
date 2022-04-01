package org.riverdell.website

import org.riverdell.website.backend.WebsiteStorageBackend
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
@Configuration
open class WebsiteConfiguration
{
    private val backend by lazy {
        WebsiteStorageBackend()
    }

    @Bean
    open fun bindBackend() = backend
}
