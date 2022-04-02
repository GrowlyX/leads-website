package org.riverdell.website.security

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * @author GrowlyX
 * @since 4/1/2022
 */
@Configuration
@EnableWebSecurity(debug = false)
open class WebsiteSecurityConfiguration(
    private val filter: WebsiteSecurityFilter
) : VaadinWebSecurityConfigurerAdapter()
{
    companion object
    {
        const val OAUTH_LOGIN = "/login"
    }

    override fun configure(
        security: HttpSecurity
    )
    {
        security.formLogin()
            .loginPage(OAUTH_LOGIN)
            .and()
            .logout()
            .deleteCookies("JWT","authenticated")
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .and()
            .addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter::class.java
            )
    }
}
