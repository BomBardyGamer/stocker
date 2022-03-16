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

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object StockTable : IntIdTable("stock") {

    val item: Column<EntityID<Int>> = reference("item_id", ItemsTable)
    val quantity: Column<Int> = integer("quantity")
}

class StockEntity(id: EntityID<Int>) : IntEntity(id) {

    var item: ItemEntity by ItemEntity referencedOn StockTable.item
    var quantity: Int by StockTable.quantity

    companion object : IntEntityClass<StockEntity>(StockTable)
}
