package org.riverdell.website.frontend.views.tutorial

import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.Html
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.HasDynamicTitle
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.loader.CompositeLoader
import org.riverdell.website.tutorial.TutorialRepository
import org.riverdell.website.users.WebsiteUser
import org.riverdell.website.users.WebsiteUserRepository
import org.vaadin.maxime.StringUtil
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author GrowlyX
 * @since 4/10/2022
 */
@Route(
    value = "tutorial/:tutorial",
    layout = SiteLayout::class
)
@AnonymousAllowed
class TutorialDetailedView : Main(), HasComponents, HasStyle, HasDynamicTitle, CompositeLoader<String>, BeforeEnterObserver
{
    private val dateFormat = SimpleDateFormat("d MMMM y")

    private val default = WebsiteUser(
        "nothing@nothing.gg",
        "???", "https://www.kindpng.com/picc/m/22-223930_avatar-person-neutral-man-blank-face-buddy-facebook.png"
    )

    private var dynamicTitle = "Loading..."

    override fun getPageTitle() =
        this.dynamicTitle

    override fun beforeEnter(
        event: BeforeEnterEvent
    )
    {
        val tutorial = event
            .routeParameters
            .get("tutorial")
            .orElse("")

        this.load(tutorial)
    }

    override fun load(t: String)
    {
        if (t.isBlank())
        {
            add(
                H3("You must provide a tutorial id!")
            )
            return
        }

        val tutorial = TutorialRepository
            .repository()
            .retrieve(t)

        if (tutorial == null)
        {
            add(
                H3(
                    "No tutorial with the ID \"$t\" was found!"
                )
            )
            return
        }

        this.dynamicTitle =
            tutorial.titleField

        addClassNames(
            "max-w-screen-lg", "mx-auto", "pb-l", "px-l"
        )

        Image(
            tutorial.image,
            "Cover Image"
        ).apply {
            addClassNames("banner")
            addClassNames("banner-margin")

            // maintain aspect ratio
            setHeight(350F, Unit.PIXELS)
        }.let {
            add(VerticalLayout().apply {
                add(it)
            })
        }

        val container = HorizontalLayout()
        container.addClassNames("items-center", "justify-between")

        val verticalLayout = VerticalLayout()
            .apply {
                isPadding = false
            }

        val header = H2(tutorial.titleField)
        header.addClassNames("mb-0", "mt-xl", "text-3xl")

        verticalLayout.add(header, Hr())

        val profileInfo = HorizontalLayout()

        val user = WebsiteUserRepository
            .repository.retrieve(tutorial.author)
            ?: default

        profileInfo.add(
            Image(
                if (user.profilePng == null)
                    user.picture else "/resources/profiles/${user.profilePng}",
                "User Profile Picture"
            ).apply {
                setHeight(65F, com.vaadin.flow.component.Unit.PIXELS)
                setWidth(65F, com.vaadin.flow.component.Unit.PIXELS)

                onLeftClick {
                    UI.getCurrent().page.setLocation("/user/${user.username}")
                }

                addClassNames("profile-picture-small")
            }
        )

        profileInfo.add(
            Span().apply {
                add(
                    Anchor(
                        "/user/${user.username}", user.username
                    ).apply {
                        addClassNames("bold")
                    }
                )

                val readTime = tutorial.content
                    .split(" ").size / (200).toDouble()

                add(
                    Paragraph(
                        dateFormat.format(
                            Date(tutorial.created)
                        ) + " \uD83D\uDF84 ${
                            readTime.toInt()
                                .coerceAtLeast(1)
                        } minute read."
                    )
                )
            }
        )

        verticalLayout.add(profileInfo)

        verticalLayout.add(
            markdown(tutorial.content)
                .apply {

                }
        )

        val imageContainer = OrderedList()
        imageContainer.addClassNames(
            "gap-m", "grid", "list-none", "m-0", "p-0"
        )

        container.add(verticalLayout)
        add(container, imageContainer)
    }

    private fun markdown(value: String): Html
    {
        val html = String.format(
            "<div>%s</div>",
            parseMarkdown(
                StringUtil.getNullSafeString(value)
            )
        )

        return Html(html)
    }

    private var parser: Parser =
        Parser.builder().build()

    private var renderer: HtmlRenderer =
        HtmlRenderer.builder().build()

    private fun parseMarkdown(value: String): String
    {
        return renderer.render(
            parser.parse(value)
        )
    }
}
