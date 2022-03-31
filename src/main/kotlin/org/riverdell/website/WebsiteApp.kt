package org.riverdell.website

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@SpringBootApplication
open class WebsiteApp
{
    companion object
    {
        @JvmStatic
        fun main(args: Array<String>)
        {
            SpringApplication.run(
                WebsiteApp::class.java, *args
            )
        }
    }
}
