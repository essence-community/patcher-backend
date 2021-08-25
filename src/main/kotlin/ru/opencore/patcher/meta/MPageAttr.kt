package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMPageAttrSqlPostgres = "INSERT INTO s_mt.t_page_attr" +
		"(ck_id, ck_page, ck_attr, cv_value, ck_user, ct_change)" +
		" VALUES (%s, %s, %s, %s, %s, %s) " +
		" on conflict (ck_id) do update set ck_page = excluded.ck_page, ck_attr = excluded.ck_attr, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MPageAttr(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_page") val ckPage: String,
	@ColumnName("ck_attr") val ckAttr: String,
	@ColumnName("cv_value") val cvValue: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMPageAttrSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckPage),
			getSqlStringOrNull(ckAttr),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}