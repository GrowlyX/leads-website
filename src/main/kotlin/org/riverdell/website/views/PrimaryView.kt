package org.riverdell.website.views

import com.example.demo.GreetService
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route

/**
 * @author GrowlyX
 * @since 3/30/2022
 */
@Route("")
@CssImport("./styles/shared-styles.css")
@CssImport(
    value = "./styles/vaadin-text-field-styles.css",
    themeFor = "vaadin-text-field"
)
class PrimaryView : VerticalLayout()
{
    init
    {
        val textField = TextField(
            "Please enter your name:"
        )

        val button = Button(
            "Say hello"
        ) {
            Notification.show("")
        }

        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        button.addClickShortcut(Key.ENTER)

        addClassName("centered-content")
        add(textField, button)
    }
}
