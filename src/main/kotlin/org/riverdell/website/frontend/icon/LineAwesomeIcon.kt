package org.riverdell.website.frontend.icon

import com.vaadin.flow.component.dependency.NpmPackage
import com.vaadin.flow.component.html.Span

/**
 * @author GrowlyX
 * @since 4/8/2022
 */
@NpmPackage(
    value = "line-awesome",
    version = "1.3.0"
)
class LineAwesomeIcon(
    private val classNames: String
) : Span()
{
    init
    {
        addClassNames("menu-item-icon")

        if (this.classNames.isNotEmpty())
        {
            addClassNames(this.classNames)
        }
    }
}
