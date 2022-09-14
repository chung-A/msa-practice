package com.example.userservice.config.db

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "user.datasource.master")
class DatasourceProperties {
    lateinit var driverClassName: String
    lateinit var url: String
    lateinit var userName: String
    lateinit var password: String
    var readOnly: Boolean = false
    var minimumIdle: Int = 10
    var maximumPoolSize: Int = 20
    var timeOut: Long = 30
}
