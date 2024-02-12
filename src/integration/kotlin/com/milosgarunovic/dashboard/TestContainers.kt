package com.milosgarunovic.dashboard

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.util.Collections.singletonMap

object TestContainers {

    val instance by lazy { startContainer() }

    // TODO dockerImageName should be the same as in docker-compose, figure out how to define for both in one place
    private fun startContainer() = PostgreSQLContainer<Nothing>("postgres:14.2-alpine3.15").apply {
        setWaitStrategy(Wait.forListeningPort())
        withTmpFs(singletonMap("/var/lib/postgresql/data", "rw"))
        start()
    }
}