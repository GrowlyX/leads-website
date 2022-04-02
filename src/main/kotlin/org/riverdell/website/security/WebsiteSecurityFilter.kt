package org.riverdell.website.security

import io.supabase.gotrue.GoTrueApi
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author GrowlyX
 * @since 4/2/2022
 */
@Component
class WebsiteSecurityFilter(
    private val configuration: WebsiteSecurityClient
) : OncePerRequestFilter()
{
    private val api = configuration::class.java
        .getField("api")
        .apply {
            isAccessible = true
        }
        .get(configuration) as GoTrueApi

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    )
    {
        val context = SecurityContextHolder.getContext()

        if (context.authentication == null)
        {
            val cookie = request.cookies
                ?.find { it.name == "JWT" }

            if (cookie != null)
            {
                try
                {
                    api.getUser(cookie.value).let {
//                        context.authentication =
//                            WebsiteSecurityRepository.repository
//                                .retrieve(it.id)
                    }
                } catch (exception: Exception)
                {
                    val oldCookie = request.cookies
                        .find { it.name == "JWT" }
                    oldCookie?.maxAge = 0

                    response.addCookie(oldCookie)
                    response.sendRedirect("/")

                    println("[DEBUG] invalid token")
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
