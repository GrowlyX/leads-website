package org.riverdell.website.frontend.views.tutorial.staff

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.tutorial.Tutorial
import org.riverdell.website.tutorial.TutorialRepository
import org.riverdell.website.users.WebsiteUser
import org.riverdell.website.users.WebsiteUserRepository
import org.riverdell.website.users.WebsiteUserSession
import org.vaadin.maxime.MarkdownArea
import java.util.UUID
import javax.annotation.security.PermitAll

/**
 * @author GrowlyX
 * @since 4/10/2022
 */
@Route(
    value = "staff/tutorials/create",
    layout = SiteLayout::class
)
@PageTitle("Staff - Tutorial Creation")
@PermitAll
class TutorialCreationView(
    private val session: WebsiteUserSession
) : KComposite()
{
    private val titleField = TextField("Title")
    private val subTitle = TextField("Subtitle")

    private val description = TextArea("Description")
    private val image = TextField("Cover Image URL")

    private val labels = TextField("Labels")

    private val cancel = Button("Cancel")
    private val save = Button("Save")

    private val binder = Binder(Tutorial::class.java)

    init
    {
        ui {
            div {
                val markdown = MarkdownArea()

                addClassName("about-view")

                val user = session.getUser().join()

                add(
                    H3("Creating new Tutorial"),
                    Paragraph("You're logged in as: ${
                        user.username
                    }")
                )

                add(
                    FormLayout().apply {
                        val fields = listOf(
                            titleField, subTitle, description,
                            image, labels
                        )

                        titleField.isRequired = true
                        subTitle.isRequired = true
                        description.isRequired = true
                        image.isRequired = true

                        for (field in fields)
                        {
                            add(field)
                        }
                    }
                )

                add(
                    verticalLayout {
                        val content = markdown
                            .apply { require(true) }

                        add(
                            H3("Content"),
                            content
                        )
                    }
                )

                add(
                    horizontalLayout {
                        addClassName("button-layout")

                        this@TutorialCreationView.save.addThemeVariants(
                            ButtonVariant.LUMO_PRIMARY,
                            ButtonVariant.LUMO_SUCCESS
                        )

                        this@TutorialCreationView.cancel.addThemeVariants(
                            ButtonVariant.LUMO_ERROR
                        )

                        add(save)
                        add(cancel)
                    }
                )

                binder.bindInstanceFields(
                    this@TutorialCreationView
                )
                binder.bean = Tutorial(
                    UUID.randomUUID().toString()
                        .split("-")[0],
                    user.email
                )

                cancel.addClickListener {
                    UI.getCurrent().page.setLocation("/")
                }

                save.addClickListener {
                    binder.bean.content =
                        markdown.input.value

                    binder.bean.created =
                        System.currentTimeMillis()

                    TutorialRepository
                        .repository()
                        .storeAsync(
                            binder.bean.uniqueId,
                            binder.bean
                        )

                    UI.getCurrent().page
                        .setLocation("/tutorials")
                }
            }
        }
    }
}