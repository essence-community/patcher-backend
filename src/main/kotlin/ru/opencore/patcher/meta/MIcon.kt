package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMIconSqlPostgres = "INSERT INTO s_mt.t_icon " + 
		"(ck_id, cv_name, cv_font, ck_user, ct_change) " + 
		"VALUES(%s, %s, %s, %s, %s) " + 
		"on conflict (ck_id) do update set cv_name = excluded.cv_name, cv_font = excluded.cv_font, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MIcon(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cv_font") val cvFont: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMIconSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(cvFont),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}