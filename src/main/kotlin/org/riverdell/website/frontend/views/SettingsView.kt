package org.riverdell.website.frontend.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.kaributools.textAlign
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.upload.FileRejectedEvent
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.FileBuffer
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.Lumo
import org.riverdell.website.frontend.SiteLayout
import org.riverdell.website.users.WebsiteUser
import org.riverdell.website.users.WebsiteUserRepository
import org.riverdell.website.users.WebsiteUserSession
import java.io.File
import java.nio.file.Files
import java.util.UUID
import javax.annotation.security.PermitAll


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
    private val username = TextField("Username", "johndoe2002")

    private val firstName = TextField("First name", "John")
    private val lastName = TextField("Last name", "Doe")

    private val aboutMe = TextArea(
        "About me",
        "22yo software developer. inactive acc, I use a new alias now."
    )

    private val cancel = Button("Cancel")
    private val save = Button("Save")

    private val binder = Binder(WebsiteUser::class.java)

    init
    {
        ui {
            div {
                var firstCheck = false

                addClassName("about-view")

                add(
                    H3("Your Settings")
                )

                val fileBuffer = FileBuffer()

                val upload = Upload(fileBuffer)
                upload.setAcceptedFileTypes("image/png", ".png")

                val maxFileSizeInBytes = 10 * 1024 * 1024 // 2MB
                upload.maxFileSize = maxFileSizeInBytes
                upload.isDropAllowed = true

                upload.addFileRejectedListener { event: FileRejectedEvent ->
                    val errorMessage = event.errorMessage
                    val notification =
                        Notification.show(
                            errorMessage,
                            5000,
                            Notification.Position.MIDDLE
                        )
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR)
                }

                upload.addFinishedListener {
                    val uniqueId = UUID.randomUUID()

                    val file = File(
                        "resources/banners",
                        "$uniqueId.png"
                    )

                    Files.copy(
                        fileBuffer.inputStream,
                        file.toPath()
                    )

                    binder.bean.bannerPng = uniqueId

                    Notification.show(
                        "Your banner has been saved!"
                    )
                }

                var valid = true

                add(
                    FormLayout().apply {
                        username.addValueChangeListener { field ->
                            if (!firstCheck)
                            {
                                firstCheck = true
                                return@addValueChangeListener
                            }

                            if (field.value.length < 5)
                            {
                                valid = false

                                Notification.show("This username is not 5 characters long!")
                                return@addValueChangeListener
                            }

                            if (field.value.length > 16)
                            {
                                valid = false

                                Notification.show("This username is is too long!")
                                return@addValueChangeListener
                            }

                            if (field.value.contains(" "))
                            {
                                valid = false

                                Notification.show("This username must not contain whitespaces!")
                                return@addValueChangeListener
                            }

                            val existing = WebsiteUserRepository
                                .repository.retrieveAll()

                            if (
                                existing.firstOrNull {
                                    it.username.equals(field.value, true)
                                } != null
                            )
                            {
                                valid = false

                                Notification.show("This username is already in use!")
                                return@addValueChangeListener
                            }

                            valid = true
                        }

                        aboutMe.apply {
                            this.isClearButtonVisible = true
                            this.isAutocorrect = true
                        }

                        this.add(
                            username,
                            firstName,
                            lastName,
                            aboutMe
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

                // ,
                //                    horizontalLayout {
                //                        add(
                //                            Button().apply {
                //                                addThemeVariants(
                //                                    ButtonVariant.LUMO_CONTRAST
                //                                )
                //
                //                                var current = UI.getCurrent()
                //                                    .element.themeList
                //                                    .contains(
                //                                        Lumo.DARK
                //                                    )
                //
                //                                text = if (!current)
                //                                {
                //                                    "Disable dark mode"
                //                                } else
                //                                {
                //                                    "Enable dark mode"
                //                                }
                //                                textAlign = "center"
                //
                //                                addClickListener {
                //                                    current = UI.getCurrent()
                //                                        .element.themeList
                //                                        .contains(
                //                                            Lumo.DARK
                //                                        )
                //
                //                                    if (!current)
                //                                    {
                //                                        UI.getCurrent()
                //                                            .element.themeList
                //                                            .remove(Lumo.LIGHT)
                //
                //                                        UI.getCurrent()
                //                                            .element.themeList
                //                                            .add(Lumo.DARK)
                //
                //                                        binder.bean.darkMode = true
                //                                    } else
                //                                    {
                //                                        UI.getCurrent()
                //                                            .element.themeList
                //                                            .remove(Lumo.DARK)
                //
                //                                        UI.getCurrent()
                //                                            .element.themeList
                //                                            .add(Lumo.LIGHT)
                //
                //                                        binder.bean.darkMode = false
                //                                    }
                //
                //                                    text = if (!current)
                //                                    {
                //                                        "Disable dark mode"
                //                                    } else
                //                                    {
                //                                        "Enable dark mode"
                //                                    }
                //                                }
                //                            }
                //                        )
                //                    }

                val header = H3("Banner")
                header.addClassNames("mb-0", "mt-xl", "text-3xl")

                val description = Paragraph(
                    "Upload a custom profile banner for other users to see."
                )
                description.addClassNames("mb-xl", "mt-0", "text-secondary")

                binder.bindInstanceFields(
                    this@SettingsView
                )
                binder.bean = session.getUser().join()

                add(
                    VerticalLayout().apply {
                        add(
                            header, description, upload, Button("Reset your Banner")
                                .apply {
                                    addThemeVariants(
                                        ButtonVariant.LUMO_ERROR
                                    )

                                    addClickListener {
                                        binder.bean.bannerPng = null

                                        Notification.show(
                                            "Your banner has been reset!"
                                        )
                                    }
                                }
                        )
                    }
                )

                cancel.addClickListener {
                    binder.bean = session.getUser().join()
                }

                save.addClickListener {
                    if (!valid)
                    {
                        Notification.show("Your username is invalid.")
                        return@addClickListener
                    }

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
