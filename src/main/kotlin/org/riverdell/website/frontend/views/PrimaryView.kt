package org.riverdell.website.frontend.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.VaadinServletRequest
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.model.WebsiteUserSession
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@Route(
    value = "",
    layout = SiteLayout::class
)
@CssImport("./styles/shared-styles.css")
@CssImport(
    value = "./styles/vaadin-text-field-styles.css",
    themeFor = "vaadin-text-field"
)
@PageTitle("Home")
@AnonymousAllowed
class PrimaryView(
    private val userSession: WebsiteUserSession
) : KComposite()
{
    init
    {
        ui {
            verticalLayout {
                if (!userSession.loggedIn())
                {
                    val div = Div()
                    div.text = "You're not logged in!"
                    div.element.style.set(
                        "font-size", "xx-large"
                    )

                    val button = button("Click to Login") {
                        onLeftClick {
                            UI.getCurrent().page.setLocation("/login")
                        }
                    }

                    alignItems = FlexComponent
                        .Alignment.CENTER

                    add(div, button)
                    return@verticalLayout
                }

                val user = userSession
                    .getUser().join()

                val div = Div()
                div.text = "Hey ${user.username}! "
                div.element.style.set(
                    "font-size", "xx-large"
                )

                val image = Image(
                    user.picture, "Profile Picture"
                )

                val button = button("Logout") {
                    onLeftClick {
                        UI.getCurrent().page.setLocation("/login")

                        val logoutHandler = SecurityContextLogoutHandler()
                        logoutHandler.logout(
                            VaadinServletRequest.getCurrent().httpServletRequest,
                            null, null
                        )
                    }
                }

                alignItems = FlexComponent
                    .Alignment.CENTER

                add(div, image, button)
            }
        }
    }
}
