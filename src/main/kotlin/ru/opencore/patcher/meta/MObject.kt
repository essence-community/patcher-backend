package ru.opencore.patcher.meta


import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMObjectSqlPostgres = "select pkg_patcher.p_merge_object(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);"

data class MObject(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_class") val ckClass: String,
	@ColumnName("ck_parent") val ckParent: String?,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cn_order") val cnOrder: String,
	@ColumnName("ck_query") val ckQuery: String?,
	@ColumnName("cv_description") val cvDescription: String?,
	@ColumnName("cv_displayed") val cvDisplayed: String?,
	@ColumnName("cv_modify") val cvModify: String?,
	@ColumnName("ck_provider") val ckProvider: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMObjectSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckClass),
			getSqlStringOrNull(ckParent),
			getSqlStringOrNull(cvName),
			cnOrder,
			getSqlStringOrNull(ckQuery),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(cvDisplayed),
			getSqlStringOrNull(cvModify),
			getSqlStringOrNull(ckProvider),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}