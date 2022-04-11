package org.riverdell.website.frontend.menu

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.ListItem
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.IronIcon
import com.vaadin.flow.router.RouterLink

/**
 * @author GrowlyX
 * @since 4/8/2022
 */
class MenuEntry(
    private val title: String,
    private val icon: Component
) : ListItem()
{
    constructor(
        title: String,
        icon: Component,
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
                icon, this
            )
        }

        add(entry)
    }

    constructor(
        title: String,
        icon: Component,
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
                (icon as IronIcon).apply {
                    addClassName("menu-item-button")
                }, this
            )
        }

        add(entry)
    }
}
