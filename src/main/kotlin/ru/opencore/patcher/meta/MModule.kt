package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMModuleSqlPostgres = "INSERT INTO s_mt.t_module (ck_id, cv_name, ck_user, ct_change, ck_view, cv_version, cv_version_api, cl_available, cc_manifest, cc_config)" +
		"VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) " +
		"on conflict (ck_id) do update set ck_id = excluded.ck_id, ck_view = excluded.ck_view, cv_name = excluded.cv_name, ck_user = excluded.ck_user, ct_change = excluded.ct_change, cv_version = excluded.cv_version, cv_version_api = excluded.cv_version_api, cl_available = excluded.cl_available, cc_manifest = excluded.cc_manifest, cc_config = excluded.cc_config;"

data class MModule(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("ck_view") val ckView: String = "system",
	@ColumnName("cv_version") val cvVersion: String,
    @ColumnName("cv_version_api") val cvVersionApi: String,
	@ColumnName("cl_available") val clAvailable: Int,
	@ColumnName("cc_manifest") val ccManifest: String,
	@ColumnName("cc_config") val ccConfig: String
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMModuleSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			getSqlStringOrNull(ckView),
			getSqlStringOrNull(cvVersion),
			getSqlStringOrNull(cvVersionApi),
			clAvailable,
			getSqlStringOrNull(ccManifest),
			getSqlStringOrNull(ccConfig)
		);
	}
}