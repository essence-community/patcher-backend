package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatLocalizationSqlPostgres = "INSERT INTO s_mt.t_localization (ck_id, ck_d_lang, cr_namespace, cv_value, ck_user, ct_change)" +
		"VALUES(%s, %s, %s, %s, %s, %s) " +
		"on conflict on constraint cin_u_localization_1 do update set ck_id = excluded.ck_id, ck_d_lang = excluded.ck_d_lang, cr_namespace = excluded.cr_namespace, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MLocalization(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_d_lang") val ckDLang: String,
	@ColumnName("cr_namespace") val crNamespace: String,
	@ColumnName("cv_value") val cvValue: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatLocalizationSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckDLang),
			getSqlStringOrNull(crNamespace),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}