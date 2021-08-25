package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatDLangSqlPostgres = "INSERT INTO s_mt.t_d_lang (ck_id, cv_name, cl_default, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set cv_name = excluded.cv_name, cl_default = excluded.cl_default, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MDLang(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cl_default") val clDefault: Int? = 0,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatDLangSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvName),
			clDefault,
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}