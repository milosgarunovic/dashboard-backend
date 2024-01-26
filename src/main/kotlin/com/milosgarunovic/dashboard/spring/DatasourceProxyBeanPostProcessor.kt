package com.milosgarunovic.dashboard.spring

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
@ConditionalOnProperty("dashboard.sql.show", havingValue = "true")
class DatasourceProxyBeanPostProcessor : BeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is DataSource) {
            return ProxyDataSourceBuilder.create(bean)
                .logQueryBySlf4j(SLF4JLogLevel.INFO)
                .build()
        }
        return bean
    }
}