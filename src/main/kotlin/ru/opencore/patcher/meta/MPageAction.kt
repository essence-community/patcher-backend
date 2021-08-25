package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatPageActionSqlPostgres = "select pkg_patcher.p_merge_page_action(%s, %s, %s, %s, %s, %s);";

data class MPageAction(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_page") val cvPage: String,
	@ColumnName("cr_type") val crType: String,
	@ColumnName("cn_action") val cnAction: Int,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatPageActionSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvPage),
			getSqlStringOrNull(crType),
			cnAction,
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}