package org.riverdell.website.frontend.views.user

import com.github.mvysny.karibudsl.v10.*
import com.openhtmltopdf.css.parser.property.PrimitivePropertyBuilders.VerticalAlign
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.details.Details
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.frontend.views.tutorial.TutorialViewCard
import org.riverdell.website.loader.CompositeLoader
import org.riverdell.website.tutorial.TutorialRepository
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
    private val static = "resources/static/default_banner.png"

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
        addClassNames("profile-view")

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

        addClassNames(
            "tutorials-view", "max-w-screen-lg",
            "mx-auto", "pb-l", "px-l"
        )

        Image(
            if (user.bannerPng == null)
                static else "resources/banners/${user.bannerPng}",
            "Banner"
        ).apply {
            addClassNames("banner")

            // maintain aspect ratio
            setHeight(350F, Unit.PIXELS)
        }.apply {
            this@UserView.add(this)
        }

        val container = HorizontalLayout()
        container.addClassNames("items-center", "justify-between")

        val verticalLayout = VerticalLayout()

        val header = H2(user.username)
        header.addClassNames("mb-0", "mt-xl", "text-3xl")
        header.addClassNames(user.role.name.lowercase())

        val description = Paragraph(
            "${
                user.role.display
            } \uD83D\uDF84 Account created on ${
                TutorialViewCard.dateFormat
                    .format(user.firstLogin)
            } at ${
                TutorialViewCard.dateFormatSpecific
                    .format(user.firstLogin)
            }."
        )
        description.addClassNames(
            "mb-xl", "mt-0", "text-secondary"
        )

        verticalLayout.add(
            header, description
        )

        verticalLayout.isSpacing = false

        val imageContainer = OrderedList()
        imageContainer.addClassNames(
            "gap-m", "grid", "list-none", "m-0", "p-0"
        )

        if (user.socialMedia.isNotEmpty())
        {
            val components =
                mutableListOf<Component>()

            for (media in user.socialMedia)
            {
                components.add(
                    Anchor(
                        media.key.template.format(media.value),
                        " \uD83D\uDF84 " + media.key.fancy
                    )
                )
            }

            val socialMedia = Span(
                *components.toTypedArray()
            )

            socialMedia.addClassNames(
                "mb-xl", "mt-0", "text-secondary"
            )

            verticalLayout.add(socialMedia)
        }

        container.add(
            Image(
                if (user.profilePng == null)
                    user.picture else "/resources/profiles/${user.profilePng}",
                "User Profile Picture"
            ).apply {
                setHeight(150F, Unit.PIXELS)
                setWidth(150F, Unit.PIXELS)

                addClassNames("profile-picture")
            }
        )

        container.add(
            verticalLayout
        )

        add(container)

        if (user.aboutMe.isNotEmpty())
        {
            val layout = VerticalLayout()
                .apply {
                    add(Paragraph(user.aboutMe).apply {
                        addClassNames(
                            "mb-xl", "mt-0", "text-secondary"
                        )
                    })

                    isSpacing = false
                    isPadding = false
                }

            add(
                Details(
                    "About me", layout
                )
            )
        }

        add(imageContainer)

        val cards = mutableListOf<TutorialViewCard>()

        val tutorials = TutorialRepository
            .repository().retrieveAll()

        val refresh: OrderedList.() -> kotlin.Unit = {
            for (card in cards)
            {
                this.remove(card)
            }

            for (tutorial in tutorials.filter {
                it.author.equals(user.email, true)
            })
            {
                this.add(
                    TutorialViewCard(tutorial)
                        .apply {
                            cards.add(this)
                        }
                )
            }
        }

        imageContainer.refresh()
    }
}
