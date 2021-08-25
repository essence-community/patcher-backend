package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatAttrTypeSqlPostgres = "INSERT INTO s_mt.t_attr_type(ck_id, cv_name, cv_description, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set cv_description = excluded.cv_description, cv_name = excluded.cv_name, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MAttrType(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cv_description") val cvDescription: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatAttrTypeSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}