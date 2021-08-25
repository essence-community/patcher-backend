package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMPageObjectAttrSqlPostgres = "select pkg_patcher.p_merge_page_object_attr(%s, %s, %s, %s, %s, %s, %s);";

data class MPageObjectAttr(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_page_object") val ckPageObject: String,
	@ColumnName("ck_class_attr") val ckClassAttr: String,
	@ColumnName("cv_value") val cvValue: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
    @ColumnName("ck_attr") val ckAttr: String
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMPageObjectAttrSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckPageObject),
			getSqlStringOrNull(ckClassAttr),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			getSqlStringOrNull(ckAttr)
		);
	}
}