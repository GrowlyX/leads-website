package org.riverdell.website.frontend.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.riverdell.website.frontend.SiteLayout
import javax.annotation.security.PermitAll
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.notification.Notification

import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import org.riverdell.website.users.WebsiteUser
import org.riverdell.website.users.WebsiteUserRepository
import org.riverdell.website.users.WebsiteUserSession


/**
 * @author GrowlyX
 * @since 4/9/2022
 */
@Route(
    value = "user/options",
    layout = SiteLayout::class
)
@PageTitle("Options")
@PermitAll
class SettingsView(
    private val session: WebsiteUserSession
) : KComposite()
{
    private val firstName = TextField("First name", "John")
    private val lastName = TextField("Last name", "Doe")
    private val email = EmailField("Email address")

    private val cancel = Button("Cancel")
    private val save = Button("Save")

    private val binder = Binder(WebsiteUser::class.java)

    init
    {
        ui {
            div {
                addClassName("about-view")

                add(
                    H3("Your Settings")
                )

                add(
                    FormLayout().apply {
                        email.errorMessage = "Please enter a valid email."

                        this.add(
                            firstName,
                            lastName,
                            email
                        )
                    }
                )

                add(
                    horizontalLayout {
                        addClassName("button-layout")

                        this@SettingsView.save.addThemeVariants(
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_SUCCESS
                        )

                        this@SettingsView.cancel.addThemeVariants(
                            ButtonVariant.LUMO_ERROR
                        )

                        add(save)
                        add(cancel)
                    }
                )

                binder.bindInstanceFields(
                    this@SettingsView
                )
                binder.bean = session.getUser().join()

                cancel.addClickListener {
                    binder.bean = session.getUser().join()
                }

                save.addClickListener {
                    WebsiteUserRepository.repository
                        .storeAsync(binder.bean.email, binder.bean)

                    Notification.show(
                        "Saved settings successfully!"
                    )
                }
            }
        }
    }
}