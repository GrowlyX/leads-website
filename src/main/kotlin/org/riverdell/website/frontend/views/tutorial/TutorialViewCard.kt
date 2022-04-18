package org.riverdell.website.frontend.views.tutorial

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.html.*
import org.riverdell.website.tutorial.Tutorial
import org.riverdell.website.users.WebsiteUserRepository

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
class TutorialViewCard(
    tutorial: Tutorial
) : ListItem()
{
    init
    {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l")

        val div = Div()
        div.addClassNames(
            "bg-contrast", "flex items-center", "justify-center",
            "mb-m", "overflow-hidden", "rounded-m w-full"
        )
        div.height = "160px"

        val image = Image()
        image.src = tutorial.image

        image.addClickListener {
            UI.getCurrent().page.setLocation("/tutorial/${
                tutorial.uniqueId
            }")
        }

        image.setHeight(200F, Unit.PIXELS)
        image.setWidth(350F, Unit.PIXELS)

        image.setAlt(tutorial.title)

        div.add(image)

        val header = Span()
        header.addClassNames("text-xl", "font-semibold")
        header.text = tutorial.title

        header.addClickListener {
            UI.getCurrent().page.setLocation("/tutorial/${
                tutorial.uniqueId
            }")
        }

        val subtitle = Span()
        subtitle.addClassNames("text-s", "text-secondary")
        subtitle.text = tutorial.subTitle

        val description = Paragraph(
            tutorial.description
        ).apply {
            addClassNames("text-s", "text-secondary")
        }

        this.add(div, header, subtitle, description)

        val span = Span()
        span.addClassNames(
            "labels"
        )

        for (label in tutorial.labels.split(", "))
        {
            val badge = Span()
            badge.element.setAttribute("theme", "badge")
            badge.text = label

            span.add(badge)
        }

        add(span)

        val authorSpan = Span()
        authorSpan.addClassNames("author")

        val author = WebsiteUserRepository
            .repository.retrieve(tutorial.author)

        val username = Span()
        username.addClassNames(
            "font-medium", "text-s", "text-secondary"
        )
        username.text = "by ${author?.username ?: "???"}"

        authorSpan.add(
            username,
            Avatar(author?.username, author?.picture)
                .apply {
                    addClassNames("me-xs")
                }
        )

        if (author != null)
        {
            authorSpan.addClickListener {
                UI.getCurrent().page.setLocation("/user/${author.username}")
            }
        }

        add(authorSpan)
    }
}
