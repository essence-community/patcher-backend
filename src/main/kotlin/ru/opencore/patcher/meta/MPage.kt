package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatPageSqlPostgres = "INSERT INTO s_mt.t_page (ck_id, ck_parent, cr_type, cv_name, cn_order, cl_static, cv_url, ck_icon, ck_view, ck_user, ct_change, cl_menu, cv_redirect_url, cl_multi)" +
		"VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_parent = excluded.ck_parent, ck_view=excluded.ck_view, cr_type = excluded.cr_type, cv_name = excluded.cv_name, cn_order = excluded.cn_order, cl_static = excluded.cl_static, cv_url = excluded.cv_url, ck_icon = excluded.ck_icon, ck_user = excluded.ck_user, ct_change = excluded.ct_change, cl_menu = excluded.cl_menu, cl_multi = excluded.cl_multi, cv_redirect_url = excluded.cv_redirect_url;"

data class MPage(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_parent") val ckParent: String?,
	@ColumnName("cr_type") val crType: String,
	@ColumnName("cv_name") val cvName: String?,
	@ColumnName("cn_order") val cnOrder: String,
	@ColumnName("cl_static") val clStatic: Int,
	@ColumnName("cv_url") val cvUrl: String?,
	@ColumnName("ck_icon") val ckIcon: String?,
	@ColumnName("ck_view") val ckView: String? = "system",
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("cl_menu") val clMenu: Int,
	@ColumnName("cv_redirect_url") val cvRedirectUrl: String?,
	@ColumnName("cl_multi") val clMulti: Int
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatPageSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckParent),
		    crType,
			getSqlStringOrNull(cvName),
			cnOrder,
			clStatic,
			getSqlStringOrNull(cvUrl),
			getSqlStringOrNull(ckIcon),
			getSqlStringOrNull(ckView),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			clMenu,
			getSqlStringOrNull(cvRedirectUrl),
			clMulti
		);
	}
}