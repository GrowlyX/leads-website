package org.riverdell.website

import com.vaadin.flow.component.dependency.NpmPackage
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@Theme(
    value = "stolen-cobalt",
    variant = Lumo.DARK
)
@NpmPackage(
    value = "line-awesome",
    version = "1.3.0"
)
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
