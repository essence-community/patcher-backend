package ru.opencore.patcher.auth

import java.sql.Timestamp
import org.jdbi.v3.core.mapper.reflect.ColumnName
import ru.opencore.patcher.meta.Meta
import ru.opencore.patcher.meta.getSqlStringOrNull
import ru.opencore.patcher.meta.getTimestampPostgres

var formatActionSqlPostgres =
	"INSERT INTO s_at.t_action (ck_id, cv_name, cv_description, ck_user, ct_change)" +
    " VALUES(%s, %s, %s, %s, %s) " +
    "on conflict (ck_id) do update set ck_id = excluded.ck_id, cv_name = excluded.cv_name, cv_description = excluded.cv_description, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n";

data class Action(
	@ColumnName("ck_id") val ckId: Int,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cv_description") val cvDescription: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatActionSqlPostgres,
			ckId,
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}