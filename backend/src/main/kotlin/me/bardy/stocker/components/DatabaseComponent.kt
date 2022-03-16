package me.bardy.stocker.components

import javax.sql.DataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DatabaseComponent {

    @Bean
    fun database(dataSource: DataSource, transactionManager: TransactionManager): Database {
        return Database.connect(dataSource, manager = { _ -> transactionManager })
    }
}
