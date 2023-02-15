package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMPageAttrDeleteSqlPostgres = "delete from s_mt.t_page_attr " +
		"where ck_page = %s;"

data class MPageAttrDelete(@ColumnName("ck_page") val ckPage: String): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMPageAttrDeleteSqlPostgres,
			getSqlStringOrNull(ckPage)
		);
	}
}