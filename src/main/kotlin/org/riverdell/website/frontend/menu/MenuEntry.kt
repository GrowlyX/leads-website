package org.riverdell.website.frontend.menu

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.ListItem
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.IronIcon
import com.vaadin.flow.router.RouterLink
import org.vaadin.maxime.Link

/**
 * @author GrowlyX
 * @since 4/8/2022
 */
class MenuEntry(
    private val title: String
) : ListItem()
{
    constructor(
        title: String,
        icon: Icon,
        reference: String
    ) : this(title)
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
                icon.apply {
                    this.addClassNames("menu-item-icon")
                },
                this
            )
        }

        add(entry)
    }

    constructor(
        title: String,
        icon: Icon,
        view: Class<out Component>
    ) : this(title)
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

            entry.add(icon.apply {
                this.addClassNames("menu-item-icon")
            }, this)
        }

        add(entry)
    }

    constructor(
        title: String,
        icon: Icon,
        click: () -> Unit
    ) : this(title)
    {
        val entry = Anchor()
            .apply {
                addClassNames("menu-item-link")
                addClickListener { click.invoke() }
            }

        Span(this.title).apply {
            addClassNames(
                "menu-item-text"
            )

            entry.add(icon.apply {
                this.addClassNames("menu-item-icon")
            }, this)
        }

        add(entry)
    }
}
