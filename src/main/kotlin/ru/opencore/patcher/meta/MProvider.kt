package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatProviderSqlPostgres = "INSERT INTO s_mt.t_provider (ck_id, cv_name, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_id = excluded.ck_id, cv_name = excluded.cv_name, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MProvider(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatProviderSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}