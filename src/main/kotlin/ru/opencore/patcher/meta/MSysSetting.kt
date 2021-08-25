package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMSysSettingSqlPostgres = "INSERT INTO s_mt.t_sys_setting (ck_id, cv_value, ck_user, ct_change, cv_description)" +
		"VALUES(%s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_id = excluded.ck_id, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change, cv_description = excluded.cv_description;"

data class MSysSetting(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_value") val cvValue: String?,
	@ColumnName("ck_user") val ckUser: String? = "-11",
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("cv_description") val cvDescription: String?
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMSysSettingSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			getSqlStringOrNull(cvDescription)
		);
	}
}