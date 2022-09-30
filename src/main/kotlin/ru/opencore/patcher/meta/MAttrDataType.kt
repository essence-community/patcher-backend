package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatAttrDataTypeSqlPostgres = "INSERT INTO s_mt.t_d_attr_data_type(ck_id, cv_description, cl_extra, ck_parent, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set cv_description = excluded.cv_description, cl_extra = excluded.cl_extra, ck_parent = excluded.ck_parent, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MAttrDataType(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_description") val cvDescription: String?,
    @ColumnName("cl_extra") val clExtra: Int,
	@ColumnName("ck_parent") val ckParent: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatAttrDataTypeSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvDescription),
			clExtra,
			getSqlStringOrNull(ckParent),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}