package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatPageObjectSqlPostgres = "INSERT INTO s_mt.t_page_object (ck_id, ck_page, ck_object, cn_order, ck_parent, ck_master, ck_user, ct_change)" +
		" VALUES (%s, %s, %s, %s, %s, %s, %s, %s) " +
		" on conflict (ck_id) do update set ck_page = excluded.ck_page, ck_object = excluded.ck_object, cn_order = excluded.cn_order, ck_parent = excluded.ck_parent, ck_master = excluded.ck_master, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MPageObject(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_page") val cvPage: String,
	@ColumnName("ck_object") val ckObject: String,
	@ColumnName("cn_order") val cnOrder: String,
	@ColumnName("ck_parent") val ckParent: String?,
	@ColumnName("ck_master") val ckMaster: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("ck_parent_object") val ckParentObject: String?
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatPageObjectSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvPage),
			getSqlStringOrNull(ckObject),
			cnOrder,
			getSqlStringOrNull(ckParent),
			"null",
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}