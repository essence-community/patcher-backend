package ru.opencore.patcher

import java.io.IOException

enum class DBType(val type: String) {
	POSTGRES("postgresql"),
	ORACLE("oracle");
}

fun typeDBOf(type: String): DBType {
	var str = type.trim().toLowerCase();
	for (t in DBType.values()) {
		if (t.type.equals(str)) return t
	}
	throw IOException("Not found type")
}