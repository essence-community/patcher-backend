package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMMessageSqlPostgres = "INSERT INTO s_mt.t_message (ck_id, cr_type, cv_text, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_id = excluded.ck_id, cr_type = excluded.cr_type, cv_text = excluded.cv_text, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MMessage(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cr_type") val crType: String,
	@ColumnName("cv_text") val cvText: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMMessageSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(crType),
			getSqlStringOrNull(cvText),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}