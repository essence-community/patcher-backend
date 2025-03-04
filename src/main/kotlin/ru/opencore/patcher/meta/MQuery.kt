package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatQuerySqlPostgres = "INSERT INTO s_mt.t_query (ck_id, ck_provider, ck_user, ct_change, cr_type, cr_access, cn_action, cr_cache, cv_cache_key_param, cc_query)%n" +
		" VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s,%n %s%n)%n " +
		"on conflict (ck_id) do update set cc_query = excluded.cc_query, ck_provider = excluded.ck_provider, ck_user = excluded.ck_user, ct_change = excluded.ct_change, cr_type = excluded.cr_type, cr_access = excluded.cr_access, cn_action = excluded.cn_action, cv_description = excluded.cv_description, cr_cache = excluded.cr_cache, cv_cache_key_param = excluded.cv_cache_key_param;"

data class MQuery(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cc_query") val ccQuery: String?,
	@ColumnName("ck_provider") val ckProvider: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("cr_type") val crType: String,
	@ColumnName("cr_access") val crAccess: String,
	@ColumnName("cn_action") val cnAction: String?,
	@ColumnName("cv_description") val cvDescription: String?,
	@ColumnName("cr_cache") val crCache: String?,
	@ColumnName("cv_cache_key_param") val cvCacheKeyParam: String?
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatQuerySqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckProvider),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			getSqlStringOrNull(crType),
			getSqlStringOrNull(crAccess),
			cnAction,
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(crCache),
			getSqlStringOrNull(cvCacheKeyParam),
			getSqlStringOrNull(ccQuery)
		);
	}
}