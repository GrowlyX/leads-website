package org.riverdell.website.frontend.views.defaults

import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.*
import org.riverdell.website.frontend.SiteLayout
import javax.servlet.http.HttpServletResponse

/**
 * @author GrowlyX
 * @since 4/9/2022
 */
@ParentLayout(
    SiteLayout::class
)
class ErrorView : VerticalLayout(), HasErrorParameter<NotFoundException>
{
    private val explanation = Span()

    init
    {
        add(
            H1("The page you were looking for was not found."),
            explanation
        )
    }

    override fun setErrorParameter(
        event: BeforeEnterEvent,
        parameter: ErrorParameter<NotFoundException>
    ): Int
    {
        explanation.text = "Could not navigate to '${event.location.path}'."
        return HttpServletResponse.SC_NOT_FOUND
    }
}
