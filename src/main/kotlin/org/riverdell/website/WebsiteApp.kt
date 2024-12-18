package org.riverdell.website

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import java.io.File

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@SpringBootApplication
open class WebsiteApp : SpringBootServletInitializer()
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
