package org.riverdell.website.frontend.views.tutorial

import com.github.mvysny.karibudsl.v10.h2
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Main
import com.vaadin.flow.component.html.OrderedList
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.tutorial.Tutorial
import org.riverdell.website.tutorial.TutorialRepository


/**
 * @author GrowlyX
 * @since 4/10/2022
 */
@Route(
    value = "tutorials",
    layout = SiteLayout::class
)
@PageTitle("Tutorials")
@AnonymousAllowed
class TutorialView : Main(), HasComponents, HasStyle
{
    private val sorts = mutableMapOf<String, (Tutorial) -> Long>()
        .apply {
            this["Recently created"] = {
                it.created
            }

            this["the other created"] = {
                -it.created
            }
        }

    private val repository =
        TutorialRepository
            .repository()

    init
    {
        addClassNames(
            "hello-world-view", "max-w-screen-lg",
            "mx-auto", "pb-l", "px-l"
        )

        val container = HorizontalLayout()
        container.addClassNames("items-center", "justify-between")

        val headerContainer = VerticalLayout()

        val header = H2("Technology Tutorials")
        header.addClassNames("mb-0", "mt-xl", "text-3xl")

        val description = Paragraph(
            "Something something something - TODO: bobby help change this"
        )
        description.addClassNames("mb-xl", "mt-0", "text-secondary")

        headerContainer.add(header, description)

        val available = sorts.keys.toList()

        val select = Select<String>()
        select.label = "Sort by"
        select.setItems(
            *available.toTypedArray()
        )
        select.value = available[0]

        val imageContainer = OrderedList()
        imageContainer.addClassNames(
            "gap-m", "grid", "list-none", "m-0", "p-0"
        )

        container.add(header, select)
        add(container, imageContainer)

        imageContainer.apply {
            val tutorials = repository
                .retrieveAll()

            for (tutorial in tutorials)
            {
                this.add(
                    TutorialViewCard(tutorial)
                        .apply {
                            addClickListener {
                                UI.getCurrent().page.setLocation("/tutorial/${
                                    tutorial.uniqueId
                                }")
                            }
                        }
                )
            }
        }
    }
}