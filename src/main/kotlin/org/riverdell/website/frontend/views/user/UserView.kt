package org.riverdell.website.frontend.views.user

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.loader.CompositeLoader
import org.riverdell.website.users.WebsiteUserRepository
import org.riverdell.website.users.WebsiteUserSession

/**
 * @author GrowlyX
 * @since 4/13/2022
 */
@Route(
    value = "user/:username",
    layout = SiteLayout::class
)
@AnonymousAllowed
class UserView : VerticalLayout(), BeforeEnterObserver, CompositeLoader<String>
{
    override fun beforeEnter(
        event: BeforeEnterEvent
    )
    {
        val username = event
            .routeParameters
            .get("username")
            .orElse("")

        this.load(username)
    }

    override fun load(t: String)
    {
        alignItems = FlexComponent
            .Alignment.CENTER

        if (t.isBlank())
        {
            add(
                H3("You must provide a user!")
            )
            return
        }

        val user = WebsiteUserRepository
            .repository.retrieveAll()
            .firstOrNull {
                it.username.equals(t, true)
            }

        if (user == null)
        {
            add(
                H3(
                    "No user with the username \"$t\" was found!"
                )
            )
            return
        }

        val horizontal = HorizontalLayout()
            .apply {
                add(
                    Image(user.picture, "Something")
                )

                add(
                    user.username
                )
            }

        if (user.bannerPng != null)
        {
            add(
                Image(
                    "http://localhost:8080/resources/banners/${user.bannerPng}",
                    "Banner"
                )
            )
        }
    }
}
