package org.riverdell.website.frontend

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.avatar.Avatar
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.contextmenu.ContextMenu
import com.vaadin.flow.component.html.*
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.server.VaadinServletRequest
import org.riverdell.website.frontend.menu.MenuEntry
import org.riverdell.website.frontend.views.LoginView
import org.riverdell.website.frontend.views.LogoutView
import org.riverdell.website.frontend.views.PrimaryView
import org.riverdell.website.frontend.views.SettingsView
import org.riverdell.website.model.WebsiteUserSession
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler

/**
 * @author Orf1, GrowlyX
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
    }

    private fun createDrawerContent(): Component
    {
        val appName = H2("RD Leads")
        appName.addClassNames("app-name")

        val section = Section(appName, createNavbarButtons(), createNavbarFooter())
        section.addClassNames("drawer-section")

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

    private fun entries(): Array<MenuEntry>
    {
        return if (userSession.loggedIn())
        {
            val user = this.userSession
                .getUser().join()

            arrayOf(
                MenuEntry("Home", "la la-home", PrimaryView::class.java),
                MenuEntry("Logout", "las la-sign-out-alt", LogoutView::class.java),
                MenuEntry("Options", "la la-cog", SettingsView::class.java),
                MenuEntry("Tutorials", "la la-globe", SettingsView::class.java)
            ).apply {
                if (user.isStaff())
                {
//                    MenuEntry("Staff Panel")
                }
            }
        } else arrayOf(
            MenuEntry("Home", "la la-home", PrimaryView::class.java),
            MenuEntry("Login", "la la-lock", LoginView::class.java)
        )
    }

    private fun createNavbarFooter(): Footer
    {
        val layout = Footer()
        layout.addClassNames("footer")

        if (!userSession.loggedIn())
        {
            val loginLink = Anchor(
                "/login", "Login"
            )

            loginLink.element.setAttribute(
                "router-ignore", true
            )

            layout.add(loginLink)
            return layout
        }

        val user = userSession.getUser().join()

        val avatar = Avatar(
            user.username, user.picture
        )

        avatar.addClassNames("me-xs")
        attachContextMenu(avatar)

        val name = Span(user.username)

        name.addClassNames(
            "font-medium", "text-s", "text-secondary"
        )
        layout.add(avatar, name)

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
    private fun attachContextMenu(avatar: Avatar)
    {
        val contextMenu = ContextMenu(avatar)
        contextMenu.isOpenOnClick = true
        contextMenu.addItem("Logout") {
            val logoutHandler = SecurityContextLogoutHandler()

            logoutHandler.logout(
                VaadinServletRequest.getCurrent().httpServletRequest,
                null, null
            )
        }
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
        val title = content.javaClass
            .getAnnotation(PageTitle::class.java)

        return title?.value ?: "Page - Site"
    }
}
