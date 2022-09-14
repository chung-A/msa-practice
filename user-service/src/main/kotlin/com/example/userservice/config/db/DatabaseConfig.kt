package com.example.userservice.config.db

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(DatasourceProperties::class)
@PropertySource("classpath:/db/database-default.properties")
@EnableJpaRepositories(
    basePackages = ["com.example.userservice.repository"],
    entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "userTransactionManager"
)
class DatabaseConfig {

    @Bean
    fun userDatasource(datasourceProperties: DatasourceProperties): DataSource {
        return configureDataSource(datasourceProperties)
    }

    @Bean
    fun userTransactionManager(userEntityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(userEntityManagerFactory)
    }

    @Bean
    fun userEntityManagerFactory(
        userDataSource: DataSource,
        jpaProperties: JpaProperties
    ): LocalContainerEntityManagerFactoryBean {
        val adapter = HibernateJpaVendorAdapter()
        adapter.setShowSql(jpaProperties.isShowSql)
        adapter.setDatabasePlatform(jpaProperties.databasePlatform)
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl)
        return EntityManagerFactoryBuilder(adapter, jpaProperties.properties, null)
            .dataSource(userDataSource)
            .packages("com.example.userservice.model")
            .persistenceUnit("user")
            .build()
    }

    /**
     * - reference http://brettwooldridge.github.io/HikariCP/ https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
     */
    protected fun configureDataSource(databaseProperties: DatasourceProperties): DataSource {
        return HikariDataSource().apply {
            this.connectionTestQuery = "select 1"
            this.driverClassName = databaseProperties.driverClassName
            this.jdbcUrl = databaseProperties.url
            this.username = databaseProperties.userName
            this.password = databaseProperties.password
            this.maximumPoolSize = databaseProperties.maximumPoolSize
            this.connectionTimeout = databaseProperties.timeOut
            this.minimumIdle = databaseProperties.minimumIdle
            this.addDataSourceProperty("cachePrepStmts", "true")
            this.addDataSourceProperty("prepStmtCacheSize", "250")
            this.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }
    }
}
