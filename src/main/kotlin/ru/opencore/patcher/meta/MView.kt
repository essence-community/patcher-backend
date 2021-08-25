package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMViewSqlPostgres = "INSERT INTO s_mt.t_view" +
		"(ck_id, cv_description, cct_config, cct_manifest, cl_available, ck_user, ct_change)" +
		" VALUES (%s, %s, %s::jsonb, %s::jsonb, %s, %s, %s) " +
		" on conflict (ck_id) do update set cl_available = excluded.cl_available, cct_config = excluded.cct_config, cct_manifest = excluded.cct_manifest, cv_description = excluded.cv_description, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MView(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_description") val cvDescription: String,
	@ColumnName("cct_config") val cctConfig: String? = "{}",
	@ColumnName("cct_manifest") val cctManifest: String? = "{}",
	@ColumnName("cl_available") val clAvailable: Int = 1,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMViewSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvDescription),
			getSqlStringOrNull(cctConfig),
			getSqlStringOrNull(cctManifest),
			clAvailable,
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}