package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMObjectAttrSqlPostgres = 
		"select pkg_patcher.p_merge_object_attr(%s, %s, %s, %s, %s, %s, %s);"

data class MObjectAttr(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_object") val ckObject: String,
	@ColumnName("ck_class_attr") val ckClassAttr: String,
	@ColumnName("cv_value") val cvValue: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("ck_attr") val ckAttr: String
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMObjectAttrSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckObject),
			getSqlStringOrNull(ckClassAttr),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			getSqlStringOrNull(ckAttr)
		);
	}
}
