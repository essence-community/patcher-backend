package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatAttrSqlPostgres = "INSERT INTO s_mt.t_attr(ck_id, cv_description, ck_attr_type, ck_d_data_type, cv_data_type_extra, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set cv_description = excluded.cv_description, ck_attr_type = excluded.ck_attr_type, ck_d_data_type = excluded.ck_d_data_type, cv_data_type_extra = excluded.cv_data_type_extra, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MAttr(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_description") val cvDescription: String,
	@ColumnName("ck_attr_type") val ckAttrType: String? = "basic",
	@ColumnName("ck_d_data_type") val ckDDataType: String? = "text",
	@ColumnName("cv_data_type_extra") val cvDataTypeExtra: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatAttrSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(ckAttrType),
			getSqlStringOrNull(ckDDataType),
			getSqlStringOrNull(cvDataTypeExtra),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}