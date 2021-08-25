package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMClassSqlPostgres = "INSERT INTO s_mt.t_class" +
		"(ck_id, cv_name, cv_description, cv_manual_documentation, cv_auto_documentation, cl_final, cl_dataset, ck_view, ck_user, ct_change)" +
		" VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) " +
		" on conflict (ck_id) do update set cv_name = excluded.cv_name, cv_description = excluded.cv_description, cv_manual_documentation = excluded.cv_manual_documentation, cv_auto_documentation = excluded.cv_auto_documentation, cl_final = excluded.cl_final, cl_dataset = excluded.cl_dataset, ck_view = excluded.ck_view, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MClass(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cv_description") val cvDescription: String,
	@ColumnName("cv_manual_documentation") val cvManualDocumentation: String?,
	@ColumnName("cv_auto_documentation") val cvAutoDocumentation: String?,
	@ColumnName("cl_final") val clFinal: Int,
	@ColumnName("cl_dataset") val clDataset: Int,
	@ColumnName("ck_view") val ckView: String = "system",
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMClassSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(cvManualDocumentation),
			getSqlStringOrNull(cvAutoDocumentation),
			clFinal,
			clDataset,
			getSqlStringOrNull(ckView),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}