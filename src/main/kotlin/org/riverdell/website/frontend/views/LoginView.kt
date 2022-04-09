package org.riverdell.website.frontend.views

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.security.WebSecurityProvider

/**
 * @author GrowlyX
 * @since 4/7/2022
 */
@Route(
    value = "login",
    layout = SiteLayout::class
)
@PageTitle("Login")
@AnonymousAllowed
class LoginView : KComposite()
{
    init
    {
        ui {
            verticalLayout {
                alignItems = FlexComponent
                    .Alignment.CENTER

                val div = Div()
                div.text = "Login"
                div.element.style.set(
                    "font-size", "xx-large"
                )

                val text = Paragraph()
                text.add("Please login with one of the following providers:")

                this.add(div, text)

                for (provider in WebSecurityProvider.values())
                {
                    val button = button(
                        "Login with ${provider.identifier}"
                    ) {
                        onLeftClick {
                            UI.getCurrent().page
                                .setLocation(provider.uri)
                        }

                        this.icon = provider.icon.create()
                        this.addThemeVariants(
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_SUCCESS
                        )
                        this.isAutofocus = true
                    }

                    this.add(button)
                }
            }
        }
    }
}
