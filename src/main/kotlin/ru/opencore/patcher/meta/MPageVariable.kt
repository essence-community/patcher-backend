package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatPageVariableSqlPostgres = "INSERT INTO s_mt.t_page_variable (ck_id, ck_page, cv_name, cv_description, cv_value, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_page = excluded.ck_page, cv_name = excluded.cv_name, cv_description = excluded.cv_description, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MPageVariable(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_page") val cvPage: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cv_description") val cvDescription: String?,
	@ColumnName("cv_value") val cvValue: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatPageVariableSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvPage),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}