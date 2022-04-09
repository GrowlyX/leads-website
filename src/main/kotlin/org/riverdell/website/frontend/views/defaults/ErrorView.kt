package org.riverdell.website.frontend.views.defaults

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.riverdell.website.frontend.SiteLayout
import javax.annotation.security.PermitAll

/**
 * @author GrowlyX
 * @since 4/9/2022
 */
@Route(
    value = "error",
    layout = SiteLayout::class
)
@PageTitle("Error")
@PermitAll
class ErrorView : KComposite()
{
    init
    {
        ui {
            verticalLayout {
                alignItems = FlexComponent
                    .Alignment.CENTER

                val div = Div()
                div.text = "Error"
                div.element.style.set(
                    "font-size", "xx-large"
                )

                val text = Paragraph()
                text.add("An internal server error occurred.")

                this.add(div, text)
            }
        }
    }
}