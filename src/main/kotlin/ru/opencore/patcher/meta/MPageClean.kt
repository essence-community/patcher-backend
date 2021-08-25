package ru.opencore.patcher.meta


import org.jdbi.v3.core.mapper.reflect.ColumnName
data class MPageClean(
	val cvPage: String
): Meta {
	override fun toPostgres(): String {
		return "select pkg_patcher.p_remove_page('${cvPage}');\r\n"
	}
}