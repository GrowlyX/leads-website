package org.riverdell.website.frontend.menu

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.ListItem
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.router.RouterLink
import org.riverdell.website.frontend.icon.LineAwesomeIcon

/**
 * @author GrowlyX
 * @since 4/8/2022
 */
class MenuEntry(
    private val title: String,
    private val icon: String
) : ListItem()
{
    constructor(
        title: String,
        icon: String,
        reference: String
    ) : this(title, icon)
    {
        val entry = Anchor()
            .apply {
                this.href = reference

                this.element.setAttribute(
                    "router-ignore", true
                )

                addClassNames(
                    "menu-item-link"
                )
            }

        Span(this.title).apply {
            addClassNames(
                "menu-item-text"
            )

            entry.add(
                LineAwesomeIcon(icon), this
            )
        }

        add(entry)
    }

    constructor(
        title: String,
        icon: String,
        view: Class<out Component>
    ) : this(title, icon)
    {
        val entry = RouterLink()
            .apply {
                addClassNames("menu-item-link")
                setRoute(view)
            }

        Span(this.title).apply {
            addClassNames(
                "menu-item-text"
            )

            entry.add(
                LineAwesomeIcon(icon), this
            )
        }

        add(entry)
    }
}
