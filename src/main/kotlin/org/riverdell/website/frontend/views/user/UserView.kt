package org.riverdell.website.frontend.views.user

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.PageTitle
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
@PageTitle("Viewing user profile...")
@AnonymousAllowed
class UserView : VerticalLayout(), BeforeEnterObserver, CompositeLoader<String>
{
    private val static = "http://localhost:8080/resources/static/default_banner.png"

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
        if (t.isBlank())
        {
            alignItems = FlexComponent
                .Alignment.CENTER

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
            alignItems = FlexComponent
                .Alignment.CENTER

            add(
                H3(
                    "No user with the username \"$t\" was found!"
                )
            )
            return
        }

        val horizontal = HorizontalLayout()
            .apply {
                alignItems = FlexComponent
                    .Alignment.START

                add(
                    Image(user.picture, "Something")
                        .apply {
                            setHeight(75F, Unit.PIXELS)
                            setWidth(75F, Unit.PIXELS)
                        }
                )

                add(
                    H3(user.username)
                )
            }

        add(
            Image(
                user.bannerPng?.let {
                    "http://localhost:8080/resources/banners/$it"
                } ?: static,
                "Banner"
            ).apply {
                alignItems = FlexComponent
                    .Alignment.CENTER

                // maintain aspect ratio
                setHeight(350F, Unit.PIXELS)
            }
        )

        add(horizontal)
    }
}
