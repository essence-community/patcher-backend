package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import com.fasterxml.jackson.databind.ObjectMapper
var mapper = ObjectMapper();

data class MCleanObjectAttr(
	val ckClass: String,
	val ckAttrs: List<String>
): Meta {
	override fun toPostgres(): String {
		return "select pkg_patcher.p_clear_attr('${ckClass}'::varchar, '${mapper.writeValueAsString(ckAttrs)}'::jsonb);"
	}
}