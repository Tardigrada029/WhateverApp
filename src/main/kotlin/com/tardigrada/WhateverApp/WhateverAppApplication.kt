package com.tardigrada.WhateverApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhateverAppApplication

fun main(args: Array<String>) {
	runApplication<WhateverAppApplication>(*args)
}
