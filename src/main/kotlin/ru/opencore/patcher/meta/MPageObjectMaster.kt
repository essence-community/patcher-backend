package ru.opencore.patcher.meta


import org.jdbi.v3.core.mapper.reflect.ColumnName


var formatMPageObjectMasterSqlPostgres = "update s_mt.t_page_object" +
		" set ck_master=%s" +
		" where ck_id=%s;"

data class MPageObjectMaster(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_master") val ckMaster: String
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMPageObjectMasterSqlPostgres,
			getSqlStringOrNull(ckMaster),
			getSqlStringOrNull(ckId)			
		);
	}
}