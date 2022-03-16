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
package me.bardy.stocker.orm

import java.time.LocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime

object ItemsTable : IntIdTable("items") {

    val gtin: Column<Int> = integer("gtin")
    val title: Column<String> = text("title")
    val icon: Column<String> = text("icon")
    val url: Column<String> = text("url")
    val timeAdded: Column<LocalDateTime> = datetime("time_added")
}

class ItemEntity(id: EntityID<Int>) : IntEntity(id) {

    var gtin: Int by ItemsTable.gtin
    var title: String by ItemsTable.title
    var icon: String by ItemsTable.icon
    var url: String by ItemsTable.url
    var timeAdded: LocalDateTime by ItemsTable.timeAdded

    companion object : IntEntityClass<ItemEntity>(ItemsTable)
}
