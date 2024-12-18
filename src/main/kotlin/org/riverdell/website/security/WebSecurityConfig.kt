package org.riverdell.website.security

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


/**
 * @author GrowlyX
 * @since 4/7/2022
 */
@Configuration
@EnableWebSecurity
open class WebSecurityConfig : VaadinWebSecurityConfigurerAdapter()
{
    override fun configure(
        http: HttpSecurity
    )
    {
        super.configure(http)

        http.oauth2Login()
            .loginPage("/login")
            .failureUrl("/")
            .permitAll()
    }

    override fun configure(web: WebSecurity)
    {
        web.ignoring()
            .antMatchers(
                "/resources/banners/*",
                "/resources/static/*",
                "/resources/profiles/*",
                "/icons/*"
            )
    }
}
