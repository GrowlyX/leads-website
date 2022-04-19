package org.riverdell.website.frontend.views.tutorial

import com.github.mvysny.karibudsl.v10.text
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.html.*
import org.riverdell.website.tutorial.Tutorial
import org.riverdell.website.users.WebsiteUserRepository
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
class TutorialViewCard(
    tutorial: Tutorial
) : ListItem()
{
    companion object
    {
        val dateFormat = SimpleDateFormat("MM/dd/yy")
        val dateFormatSpecific = SimpleDateFormat("hh:mmaa")
    }

    init
    {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l")

        val div = Div()
        div.addClassNames(
            "bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
            "rounded-m w-full"
        )
        div.height = "160px"

        val image = Image()
        image.src = tutorial.image

        image.addClickListener {
            UI.getCurrent().page.setLocation(
                "/tutorial/${
                    tutorial.uniqueId
                }"
            )
        }

        image.setHeight(200F, Unit.PIXELS)
        image.setWidth(350F, Unit.PIXELS)

        image.setAlt(tutorial.titleField)

        div.add(image)

        val header = Anchor(
            "/tutorial/${
                tutorial.uniqueId
            }"
        )
        header.addClassNames("text-xl", "font-semibold")
        header.text = tutorial.titleField

        val created = Date(tutorial.created)

        val subtitle = Span()
        subtitle.addClassNames(
            "text-s", "text-secondary"
        )

        subtitle.text = "Created on ${
            dateFormat
                .format(created)
        } at ${
            dateFormatSpecific
                .format(created)
        }."

        val description = Paragraph(tutorial.description)
        description.addClassName("my-m")

        val span = Span()
            .apply {
                addClassNames("labels")
            }

        for (label in tutorial.labels.split(", "))
        {
            val badge = Span()
            badge.element.setAttribute("theme", "badge")
            badge.text = label

            span.add(badge)
        }

        val authorSpan = Span()
        authorSpan.addClassNames("author")

        val author = WebsiteUserRepository
            .repository.retrieve(tutorial.author)

        val username = Span()
        username.addClassNames(
            "text-s", "text-secondary"
        )
        username.text = "Created by "

        username.add(
            (if (author == null) Span() else Anchor(
                "/user/${author.username}"
            )).apply {
                addClassNames(
                    "font-medium", "text-s", "text-secondary"
                )

                if (author != null)
                {
                    addClassNames(author.role.name.lowercase())
                } else
                {
                    addClassNames("link")
                }

                this.text = author?.username ?: "???"
            }
        )

        username.add(".")

        authorSpan.add(
            username
        )

        add(div, header, subtitle, description, span, authorSpan)
    }
}
