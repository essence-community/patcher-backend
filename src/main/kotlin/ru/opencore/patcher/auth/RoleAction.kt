package ru.opencore.patcher.auth

import java.sql.Timestamp
import org.jdbi.v3.core.mapper.reflect.ColumnName
import ru.opencore.patcher.meta.Meta
import ru.opencore.patcher.meta.getSqlStringOrNull
import ru.opencore.patcher.meta.getTimestampPostgres
import java.util.stream.Collectors

var formatRoleActionSqlPostgres =
	"INSERT INTO s_at.t_role_action (ck_id, ck_action, ck_role, ck_user, ct_change)" +
    " VALUES(%s, %s, %s, %s, %s) " +
    "on conflict on constraint cin_u_role_action_1 do update set ck_id = excluded.ck_id, ck_action = excluded.ck_action, ck_role = excluded.ck_role, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n";

data class RoleAction(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_action") val ckAction: Int,
	@ColumnName("ck_role") val ckRole: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatRoleActionSqlPostgres,
			getSqlStringOrNull(ckId),
			ckAction,
			getSqlStringOrNull(ckRole),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}

var formatRoleActionRowsSqlPostgres = "INSERT INTO s_at.t_role_action (ck_id, ck_action, ck_role, ck_user, ct_change)\n" +
		"select t.ck_id, t.ck_action, t.ck_role, t.ck_user, t.ct_change::timestamp from (\n" +
		"%s\n" +
		") as t \n" +
		" join s_mt.t_action a\n" +
		" on t.ck_action = a.ck_id\n" +
		"on conflict on constraint cin_u_role_action_1 do update set ck_id = excluded.ck_id, ck_action = excluded.ck_action, ck_role = excluded.ck_role, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n"

var formatRoleActionRowSqlPostgres = "    select %s as ck_id, %s as ck_action, %s as ck_role, %s as ck_user, %s as ct_change"


data class RoleActionRows(
	val rows: List<RoleAction>
): Meta {
	fun getRow(): String {
		return this.rows.stream().map { row ->
			String.format(
		    formatRoleActionRowSqlPostgres,
			getSqlStringOrNull(row.ckId),
			row.ckAction,
			getSqlStringOrNull(row.ckRole),
			getSqlStringOrNull(row.ckUser),
			getTimestampPostgres(row.ctChange)
				)
		}
		.collect(Collectors.joining("\n    union all\n"))
	}
	override fun toPostgres(): String {
		if (this.rows.isEmpty()) {
			return "";
		}
		return String.format(
			formatRoleActionRowsSqlPostgres,
			this.getRow()
		);
	}
}