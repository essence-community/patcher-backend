package ru.opencore.patcher.auth

import java.sql.Timestamp
import org.jdbi.v3.core.mapper.reflect.ColumnName
import ru.opencore.patcher.meta.Meta
import ru.opencore.patcher.meta.getSqlStringOrNull
import ru.opencore.patcher.meta.getTimestampPostgres
import java.util.stream.Collectors

var formatAccountRoleSqlPostgres =
	"INSERT INTO s_at.t_account_role (ck_id, ck_role, ck_account, ck_user, ct_change) " +
    " VALUES(%s, %s, %s, %s, %s) " +
    "on conflict on constraint cin_u_account_role_1 do update set ck_id = excluded.ck_id, ck_account = excluded.ck_account, ck_role = excluded.ck_role, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n";

data class AccountRole(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_role") val ckRole: String,
	@ColumnName("ck_account") val ckAccount: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatAccountRoleSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckRole),
			getSqlStringOrNull(ckAccount),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}

var formatAccountRoleRowsSqlPostgres = "INSERT INTO s_at.t_account_role (ck_id, ck_role, ck_account, ck_user, ct_change)\n" +
		"select t.ck_id, t.ck_role, t.ck_account, t.ck_user, t.ct_change::timestamp from (\n" +
		"%s\n" +
		") as t \n" +
		" join s_mt.t_role r\n" +
		" on t.ck_role = r.ck_id\n" +
		"on conflict on constraint cin_u_account_role_1 do update set ck_id = excluded.ck_id, ck_account = excluded.ck_account, ck_role = excluded.ck_role, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n"

var formatAccountRoleRowSqlPostgres = "    select %s as ck_id, %s as ck_role, %s as ck_account, %s as ck_user, %s as ct_change"


data class AccountRoleRows(
	val rows: List<AccountRole>
): Meta {
	fun getRow(): String {
		return this.rows.stream().map { row ->
			String.format(
		    formatAccountRoleRowSqlPostgres,
			getSqlStringOrNull(row.ckId),
			getSqlStringOrNull(row.ckRole),
			getSqlStringOrNull(row.ckAccount),
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
			formatAccountRoleRowsSqlPostgres,
			this.getRow()
		);
	}
}