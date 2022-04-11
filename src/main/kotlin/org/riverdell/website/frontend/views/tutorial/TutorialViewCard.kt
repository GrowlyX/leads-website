package org.riverdell.website.frontend.views.tutorial

import com.vaadin.flow.component.html.*
import org.riverdell.website.tutorial.Tutorial

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
            "bg-contrast", "flex items-center",
            "justify-center", "mb-m", "overflow-hidden",
            "rounded-m w-full"
        )
        div.height = "160px"

        val image = Image()
        image.width = "100%"
        image.src = tutorial.image

        image.setAlt(tutorial.title)

        div.add(image)

        val header = Span()
        header.addClassNames("text-xl", "font-semibold")
        header.text = tutorial.title

        val subtitle = Span()
        subtitle.addClassNames("text-s", "text-secondary")
        subtitle.text = tutorial.subTitle

        val description = Paragraph(
            tutorial.description
        ).apply {
            addClassName("my-m")
        }

        this.add(div, header, subtitle, description)

        for (label in tutorial.labels)
        {
            val badge = Span()
            badge.element.setAttribute("theme", "badge")
            badge.text = label

            this.add(badge)
        }
    }
}