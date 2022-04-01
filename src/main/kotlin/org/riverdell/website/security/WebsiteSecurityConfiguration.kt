package org.riverdell.website.security

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
@Configuration
@EnableWebSecurity
open class WebsiteSecurityConfiguration : VaadinWebSecurityConfigurerAdapter()
{
    companion object
    {
        const val OAUTH_LOGIN = "/login"
    }

    override fun configure(
        security: HttpSecurity
    )
    {
        super.configure(security)

        security.oauth2Login()
            .loginPage(OAUTH_LOGIN).permitAll()
    }
}
