package org.riverdell.website.frontend

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome
import com.github.mvysny.karibudsl.v10.contextMenu
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.kaributools.IconName
import com.github.mvysny.kaributools.iconName
import com.github.mvysny.kaributools.textAlign
import com.vaadin.componentfactory.ToggleButton
import com.vaadin.flow.component.ClickNotifier
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.contextmenu.ContextMenu
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.dependency.NpmPackage
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.IronIcon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.HasDynamicTitle
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.server.VaadinServletRequest
import com.vaadin.flow.theme.lumo.Lumo
import org.riverdell.website.frontend.menu.MenuEntry
import org.riverdell.website.frontend.views.LoginView
import org.riverdell.website.frontend.views.LogoutView
import org.riverdell.website.frontend.views.PrimaryView
import org.riverdell.website.frontend.views.SettingsView
import org.riverdell.website.frontend.views.tutorial.TutorialView
import org.riverdell.website.frontend.views.tutorial.staff.TutorialCreationView
import org.riverdell.website.frontend.views.tutorial.staff.TutorialManagementView
import org.riverdell.website.users.WebsiteUserRepository
import org.riverdell.website.users.WebsiteUserRole
import org.riverdell.website.users.WebsiteUserSession
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler

/**
 * @author GrowlyX
 * @since 4/8/2022
 */
class SiteLayout(
    private val userSession: WebsiteUserSession
) : AppLayout()
{
    private var viewTitle: H1? = null

    init
    {
        this.primarySection = Section.DRAWER

        addToNavbar(
            true, createNavbarHeader()
        )

        addToDrawer(
            createDrawerContent()
        )

        addAttachListener {
            if (it.isInitialAttach)
            {
                if (userSession.loggedIn())
                {
                    val user = userSession
                        .getUser().join()

                    val current = user.darkMode

                    if (current)
                    {
                        UI.getCurrent()
                            .element.themeList
                            .remove(Lumo.LIGHT)

                        UI.getCurrent()
                            .element.themeList
                            .add(Lumo.DARK)
                    } else
                    {
                        UI.getCurrent()
                            .element.themeList
                            .remove(Lumo.DARK)

                        UI.getCurrent()
                            .element.themeList
                            .add(Lumo.LIGHT)
                    }
                }
            }
        }

//
//        val users = WebsiteUserRepository
//            .repository.retrieveAllAsync()
//
//        users.thenAccept { usersCollection ->
//            val binder = Binder<String>()
//
//            val lookup = LookupField<String>()
//                .apply {
//                    alignSelf = FlexComponent
//                        .Alignment.END
//
//                    setDataProvider(
//                        DataProvider.ofCollection(
//                            usersCollection.map { it.username }
//                        )
//                    )
//
//                    label = "Users"
//
//                    setWidthFull()
//
//                    grid.addColumn { it }
//                        .setHeader("Username")
//
//                    binder.forField(this)
//                        .bind({
//                            binder.bean
//                        }, { _, it ->
//                            binder.bean = it
//                        })
//                }
//
//            binder.addValueChangeListener {
//                UI.getCurrent().page.setLocation("/user/${it.value}")
//            }
//
//            addToNavbar(false, lookup)
//        }
    }

    private fun createDrawerContent(): Component
    {
        val verticalLayout = HorizontalLayout()
            .apply {
                alignItems = FlexComponent
                    .Alignment.CENTER

                add(
                    Image("https://nj.vsand-static.com/Logos/4404.png?maxwidth=1100&maxheight=1100", "RiverDell Logo")
                        .apply {
                            this.setWidth(50F, Unit.PIXELS)
                        }
                        .apply {
                            addClassNames("app-logo")
                        },
                    H1("RD Leads")
                        .apply {
                            addClassNames("app-name")
                        },
                    Hr()
                )

                addClickListener {
                    UI.getCurrent().page.setLocation("/")
                }
            }

        val section = Section(
            verticalLayout,
            createNavbarButtons(),
            createNavbarFooter()
        )

        section.addClassNames(
            "drawer-section"
        )

        return section
    }

    private fun createNavbarButtons(): Nav
    {
        val navigator = Nav()
            .apply {
                addClassNames(
                    "menu-item-container"
                )
            }

        navigator.element.setAttribute(
            "aria-labelledby", "views"
        )

        val list = UnorderedList()
        list.addClassNames("navigation-list")

        navigator.add(list)

        for (entry in entries())
        {
            list.add(entry)
        }

        return navigator
    }

    private fun entries(): Array<out Component>
    {
        return if (userSession.loggedIn())
        {
            val user = this.userSession
                .getUser().join()

            mutableListOf<Component>(
                MenuEntry(
                    "Home",
                    VaadinIcon.HOME.create(),
                    PrimaryView::class.java
                ),
                MenuEntry(
                    "Options",
                    VaadinIcon.COG.create(),
                    SettingsView::class.java
                ),
                MenuEntry(
                    "Tutorials",
                    VaadinIcon.GLOBE.create(),
                    TutorialView::class.java
                )
            ).apply {
                if (user.role over WebsiteUserRole.STAFF)
                {
                    add(Hr())
                    add(MenuEntry(
                        "Create a Tutorial",
                        VaadinIcon.ABACUS.create(),
                        TutorialCreationView::class.java
                    ))
                    add(MenuEntry(
                        "Manage Tutorials",
                        VaadinIcon.CHART.create(),
                        TutorialManagementView::class.java
                    ))

                    addAttachListener {
                        if (it.isInitialAttach && user.role over WebsiteUserRole.STAFF)
                        {
                            Notification.show("You have staff status! Your role is: ${
                                user.role.display
                            }.")
                        }
                    }
                }
            }
            .apply {
                add(Hr())
                add(MenuEntry(
                    "Logout",
                    VaadinIcon.SIGN_OUT.create()
                ) {
                    SecurityContextLogoutHandler().logout(
                        VaadinServletRequest.getCurrent().httpServletRequest,
                        null, null
                    )
                })
            }
            .toTypedArray()
        } else arrayOf(
            MenuEntry(
                "Home",
                VaadinIcon.HOME.create(),
                PrimaryView::class.java
            ),
            MenuEntry(
                "Tutorials",
                VaadinIcon.GLOBE.create(),
                TutorialView::class.java
            ),
            Hr(),
            MenuEntry(
                "Login",
                VaadinIcon.SIGN_IN.create(),
                LoginView::class.java
            )
        )
    }

    private fun createNavbarFooter(): Footer
    {
        val layout = Footer()
        layout.addClassNames("footer")

        if (!userSession.loggedIn())
        {
            val loginLink = Anchor(
                "/login", "Click to login!"
            )

            loginLink.element.setAttribute(
                "router-ignore", true
            )

            layout.add(loginLink)
            return layout
        }

        val user = userSession.getUser().join()

        val avatar = Avatar(
            user.username,
            if (user.profilePng == null)
                user.picture else "resources/profiles/${user.profilePng}"
        )

        avatar.addClassNames("me-xs")

        val name = Span(user.username)

        name.addClassNames(
            "font-medium", "text-s", "text-secondary", "bold"
        )

        var current = UI.getCurrent()
            .element.themeList
            .contains(
                Lumo.DARK
            )

        layout.add(avatar, name, (if (current) VaadinIcon.MOON.create() else VaadinIcon.SUN_O.create()).apply {
            addClassNames("dark-mode-switch")

            onLeftClick {
                current = UI.getCurrent()
                    .element.themeList
                    .contains(
                        Lumo.DARK
                    )

                if (!current)
                {
                    UI.getCurrent()
                        .element.themeList
                        .remove(Lumo.LIGHT)

                    UI.getCurrent()
                        .element.themeList
                        .add(Lumo.DARK)

                    user.darkMode = true
                } else
                {
                    UI.getCurrent()
                        .element.themeList
                        .remove(Lumo.DARK)

                    UI.getCurrent()
                        .element.themeList
                        .add(Lumo.LIGHT)

                    user.darkMode = false
                }

                this.iconName = IconName.of(
                    if (!current) VaadinIcon.MOON else VaadinIcon.SUN_O
                )

                WebsiteUserRepository.repository
                    .storeAsync(user.email, user)
            }
        }, attachContextMenu(VaadinIcon.COG.create().apply {
            addClassNames("settings-gear")
        }))

        return layout
    }

    //Creates drawer header
    private fun createNavbarHeader(): Header
    {
        val toggle = DrawerToggle()
        toggle.addClassNames("view-toggle")
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST)
        toggle.element.setAttribute("aria-label", "Menu toggle")

        viewTitle = H1()
        viewTitle!!.addClassNames("view-title")

        val header = Header(toggle, viewTitle)
        header.addClassNames("view-header")

        return header
    }

    // Attaches context menu to avatar
    private fun attachContextMenu(icon: com.vaadin.flow.component.icon.Icon): com.vaadin.flow.component.icon.Icon
    {
        val contextMenu = ContextMenu(icon)
        contextMenu.isOpenOnClick = true

        contextMenu.addItem("Options") {
            UI.getCurrent().page.setLocation("/user/options")
        }

        contextMenu.addItem("Profile") {
            val user = userSession
                .getUser().join()

            UI.getCurrent().page
                .setLocation("/user/${user.username}")
        }

        contextMenu.add(Hr())

        contextMenu.addItem("Logout") {
            val logoutHandler = SecurityContextLogoutHandler()

            logoutHandler.logout(
                VaadinServletRequest.getCurrent().httpServletRequest,
                null, null
            )
        }

        return icon
    }

    // Updates title when user navigates
    override fun afterNavigation()
    {
        super.afterNavigation()

        viewTitle!!.text =
            getCurrentPageTitle()
    }

    // Gets current page title
    private fun getCurrentPageTitle(): String
    {
        if (content is HasDynamicTitle)
        {
            return (content as HasDynamicTitle).pageTitle
        }

        val title = content.javaClass
            .getAnnotation(PageTitle::class.java)

        return title?.value ?: "Page - Site"
    }

    @JsModule("./font-awesome-iron-iconset/far.js")
    @NpmPackage(value = "@flowingcode/font-awesome-iron-iconset", version = "3.0.0")
    class Icon(icon: String) : IronIcon("fa", icon), ClickNotifier<IronIcon?>
}
