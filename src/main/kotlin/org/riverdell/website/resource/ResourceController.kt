package org.riverdell.website.resource

import com.vaadin.flow.server.auth.AnonymousAllowed
import org.apache.commons.io.IOUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.util.*
import javax.servlet.http.HttpServletResponse

/**
 * @author GrowlyX
 * @since 4/13/2022
 */
@RestController
open class ResourceController
{
    private val nonExistent: UUID =
        UUID.randomUUID()

    @GetMapping("/resources/static/{uuid}")
    fun getStaticContent(
        @PathVariable("uuid") uuid: String,
        response: HttpServletResponse
    )
    {
        val banner = File(
            "resources/static", uuid
        )

        if (banner.exists())
        {
            response.contentType = "image/png"

            IOUtils.copy(
                banner.inputStream(),
                response.outputStream
            )
        }
    }

    @GetMapping("/resources/banners/{uuid}")
    fun getBanner(
        @PathVariable("uuid") uuid: String,
        response: HttpServletResponse
    )
    {
        val uniqueId = kotlin.runCatching {
            UUID.fromString(uuid)
        }.getOrElse {
            nonExistent
        }

        if (uniqueId == nonExistent)
        {
            return
        }

        val banner = File(
            "resources/banners",
            "$uniqueId.png"
        )

        if (banner.exists())
        {
            response.contentType = "image/png"

            IOUtils.copy(
                banner.inputStream(),
                response.outputStream
            )
        }
    }

    @GetMapping("/resources/profiles/{uuid}")
    fun getProfilePicture(
        @PathVariable("uuid") uuid: String,
        response: HttpServletResponse
    )
    {
        val uniqueId = kotlin.runCatching {
            UUID.fromString(uuid)
        }.getOrElse {
            nonExistent
        }

        if (uniqueId == nonExistent)
        {
            return
        }

        val banner = File(
            "resources/profiles",
            "$uniqueId.png"
        )

        if (banner.exists())
        {
            response.contentType = "image/png"

            IOUtils.copy(
                banner.inputStream(),
                response.outputStream
            )
        }
    }
}
