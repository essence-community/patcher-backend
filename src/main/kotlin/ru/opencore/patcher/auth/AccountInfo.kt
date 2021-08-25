package ru.opencore.patcher.auth

import java.sql.Timestamp
import org.jdbi.v3.core.mapper.reflect.ColumnName
import ru.opencore.patcher.meta.Meta
import ru.opencore.patcher.meta.getSqlStringOrNull
import ru.opencore.patcher.meta.getTimestampPostgres
import java.util.stream.Collectors

var formatAccountInfoSqlPostgres =
	"INSERT INTO s_at.t_account_info (ck_id, ck_account, ck_d_info, cv_value, ck_user, ct_change)" +
    " VALUES(%s, %s, %s, %s, %s, %s) " +
    "on conflict on constraint cin_i_account_info_1 do update set ck_id = excluded.ck_id, ck_account = excluded.ck_account, ck_d_info = excluded.ck_d_info, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n";

data class AccountInfo(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_account") val ckAccount: String,
	@ColumnName("ck_d_info") val ckDInfo: String,
	@ColumnName("cv_value") val cvValue: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatAccountInfoSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckAccount),
			getSqlStringOrNull(ckDInfo),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}

var formatAccountInfoRowsSqlPostgres = "INSERT INTO s_at.t_account_info (ck_id, ck_account, ck_d_info, cv_value, ck_user, ct_change)\n" +
		"select t.ck_id, t.ck_account, t.ck_d_info, t.cv_value, t.ck_user, t.ct_change::timestamp from (\n" +
		"%s\n" +
		") as t \n" +
		" join s_mt.t_d_info inf\n" +
		" on t.ck_d_info = inf.ck_id\n" +
		"on conflict on constraint cin_i_account_info_1 do update set ck_id = excluded.ck_id, ck_account = excluded.ck_account, ck_d_info = excluded.ck_d_info, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n"

var formatAccountInfoRowSqlPostgres = "    select %s as ck_id, %s as ck_account, %s as ck_d_info, %s as cv_value, %s as ck_user, %s as ct_change"


data class AccountInfoRows(
	val rows: List<AccountInfo>
): Meta {
	fun getRow(): String {
		return this.rows.stream().map { row ->
			String.format(
		    formatAccountInfoRowSqlPostgres,
			getSqlStringOrNull(row.ckId),
			getSqlStringOrNull(row.ckAccount),
			getSqlStringOrNull(row.ckDInfo),
			getSqlStringOrNull(row.cvValue),
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
			formatAccountInfoRowsSqlPostgres,
			this.getRow()
		);
	}
}