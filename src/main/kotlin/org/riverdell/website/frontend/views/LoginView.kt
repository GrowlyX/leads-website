package org.riverdell.website.frontend.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.security.WebSecurityProvider

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
@Route(
    value = "login",
    layout = SiteLayout::class
)
@PageTitle("Login")
@AnonymousAllowed
class LoginView : KComposite()
{
    init
    {
        ui {
            verticalLayout {
                alignItems = FlexComponent
                    .Alignment.CENTER

                for (provider in WebSecurityProvider.values())
                {
                    val loginLink = Anchor(
                        provider.uri,
                        "Login with ${provider.identifier}"
                    )

                    loginLink.element.setAttribute(
                        "router-ignore", true
                    )
                    loginLink.style.set("margin-top", "100px")

                    this.add(loginLink)
                }
            }
        }
    }
}
