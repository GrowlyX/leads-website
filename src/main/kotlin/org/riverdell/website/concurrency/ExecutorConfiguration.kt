package org.riverdell.website.concurrency

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


/**
 * @author GrowlyX
 * @since 4/7/2022
 */
@EnableAsync
@Configuration
open class ExecutorConfiguration
{
    private val executor by lazy {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 3
        executor.maxPoolSize = 3
        executor.setQueueCapacity(100)
        executor.threadNamePrefix = "req-thread-"
        executor.initialize()
    }

    @Bean("req-pool")
    open fun pool() = executor
}
