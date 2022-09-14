package com.example.cataologservice.config

import com.example.cataologservice.repository.CatalogRepository
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class DataInitializer(
    val catalogRepository: CatalogRepository
) {

    @Bean
    fun dataSourceInitializer(dataSource: DataSource): DataSourceInitializer {
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        val dataSourceInitializer = DataSourceInitializer()
        dataSourceInitializer.setDataSource(dataSource)
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator)

        if (catalogRepository.count() <= 0) {
            resourceDatabasePopulator.addScript(ClassPathResource("/data.sql"))
        }
        return dataSourceInitializer
    }
}
