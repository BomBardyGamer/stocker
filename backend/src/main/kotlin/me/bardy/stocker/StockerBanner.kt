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
package me.bardy.stocker

import org.springframework.boot.Banner
import org.springframework.core.env.Environment
import java.io.PrintStream

object StockerBanner : Banner {

    private const val BANNER = """
      ___           ___           ___           ___           ___           ___           ___     
     /\  \         /\  \         /\  \         /\  \         /\__\         /\  \         /\  \    
    /::\  \        \:\  \       /::\  \       /::\  \       /:/  /        /::\  \       /::\  \   
   /:/\ \  \        \:\  \     /:/\:\  \     /:/\:\  \     /:/__/        /:/\:\  \     /:/\:\  \  
  _\:\~\ \  \       /::\  \   /:/  \:\  \   /:/  \:\  \   /::\__\____   /::\~\:\  \   /::\~\:\  \ 
 /\ \:\ \ \__\     /:/\:\__\ /:/__/ \:\__\ /:/__/ \:\__\ /:/\:::::\__\ /:/\:\ \:\__\ /:/\:\ \:\__\
 \:\ \:\ \/__/    /:/  \/__/ \:\  \ /:/  / \:\  \  \/__/ \/_|:|~~|~    \:\~\:\ \/__/ \/_|::\/:/  /
  \:\ \:\__\     /:/  /       \:\  /:/  /   \:\  \          |:|  |      \:\ \:\__\      |:|::/  / 
   \:\/:/  /     \/__/         \:\/:/  /     \:\  \         |:|  |       \:\ \/__/      |:|\/__/  
    \::/  /                     \::/  /       \:\__\        |:|  |        \:\__\        |:|  |    
     \/__/                       \/__/         \/__/         \|__|         \/__/         \|__|    
    """

    override fun printBanner(environment: Environment, sourceClass: Class<*>, out: PrintStream) {
        out.println(BANNER)
    }
}
