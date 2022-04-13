package org.riverdell.website.resource

import org.apache.commons.io.IOUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.awt.PageAttributes.MediaType
import java.io.File
import java.util.UUID
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
}
