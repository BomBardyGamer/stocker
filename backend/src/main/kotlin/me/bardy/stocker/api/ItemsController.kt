/*
 * This file is part of the Stocker project, licensed under the GNU General Public License v3.0
 *
 * Copyright (C) 2022 Callum Jay Seabrook
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.bardy.stocker.api

import java.time.LocalDateTime
import me.bardy.stocker.orm.ItemEntity
import me.bardy.stocker.orm.ItemsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Transaction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/items")
class ItemsController(private val database: Database) {

    @PostMapping("/create", produces = ["application/json"])
    fun createItem(@RequestBody newItem: NewItem): ResponseEntity<Item> {
        val now = LocalDateTime.now()
        val item = transaction {
            ItemEntity.new {
                this.gtin = newItem.gtin
                this.title = newItem.title
                this.icon = newItem.icon
                this.url = newItem.url
                this.timeAdded = now
            }
        }
        return ResponseEntity.ok().body(Item(item.gtin, item.title, item.icon, item.url, item.timeAdded))
    }

    @GetMapping(produces = ["application/json"])
    fun getItem(@RequestParam("gtin") gtin: Int): ResponseEntity<Item> {
        val item = transaction {
            ItemEntity.find { ItemsTable.gtin eq gtin }
                .limit(1)
                .singleOrNull()
        }
        if (item == null) return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(Item(item.gtin, item.title, item.icon, item.url, item.timeAdded))
    }

    private fun <T> transaction(statement: Transaction.() -> T): T = transaction(database, statement)

    data class NewItem(val gtin: Int, val title: String, val icon: String, val url: String)

    data class Item(val gtin: Int, val title: String, val icon: String, val url: String, val timeAdded: LocalDateTime)
}
