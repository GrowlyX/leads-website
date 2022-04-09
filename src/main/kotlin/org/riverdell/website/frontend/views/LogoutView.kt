package org.riverdell.website.frontend.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.VaadinServletRequest
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.security.WebSecurityProvider
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import javax.annotation.security.PermitAll

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
@Route(
    value = "logout",
    layout = SiteLayout::class
)
@PageTitle("Logout")
@PermitAll
class LogoutView : KComposite()
{
    init
    {
        ui {
            verticalLayout {
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

                add(button)
            }
        }
    }
}
