package ru.opencore.patcher.auth

import java.sql.Timestamp
import org.jdbi.v3.core.mapper.reflect.ColumnName
import ru.opencore.patcher.meta.Meta
import ru.opencore.patcher.meta.getSqlStringOrNull
import ru.opencore.patcher.meta.getTimestampPostgres

var formatDInfoSqlPostgres =
	"INSERT INTO s_at.t_d_info (ck_id, cv_description, cr_type, cl_required, ck_user, ct_change) " +
    " VALUES(%s, %s, %s, %s, %s, %s) " +
    "on conflict (ck_id) do update set ck_id = excluded.ck_id, cv_description = excluded.cv_description, cr_type = excluded.cr_type, cl_required = excluded.cl_required, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n";

data class DInfo(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_description") val cvDescription: String,
	@ColumnName("cr_type") val crType: String,
	@ColumnName("cl_required") val clRequired: Int? = 0,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatDInfoSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(crType),
			clRequired,
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}