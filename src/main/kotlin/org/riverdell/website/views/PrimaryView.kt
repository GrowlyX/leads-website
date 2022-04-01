package org.riverdell.website.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.notification.Notification
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
class PrimaryView : KComposite()
{
    init
    {
        ui {
            horizontalLayout {
                button("Say hellooo") {
                    onLeftClick {
                        Notification.show("hiii")
                    }

                    addThemeVariants(
                        ButtonVariant.MATERIAL_CONTAINED
                    )

                    addClickShortcut(Key.ENTER)
                }
            }
        }
    }
}
