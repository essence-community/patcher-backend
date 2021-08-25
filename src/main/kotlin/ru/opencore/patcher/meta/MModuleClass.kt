package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMModuleClassSqlPostgres = "INSERT INTO s_mt.t_module_class (ck_module, ck_class, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_module = excluded.ck_module, ck_class = excluded.ck_class, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MModuleClass(
	@ColumnName("ck_module") val ckModule: String,
	@ColumnName("ck_class") val ckClass: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMModuleClassSqlPostgres,
			getSqlStringOrNull(ckModule),
			getSqlStringOrNull(ckClass),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}
