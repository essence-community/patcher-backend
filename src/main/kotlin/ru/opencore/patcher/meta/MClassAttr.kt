package ru.opencore.patcher.meta
import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMClassAttrSqlPostgres = "INSERT INTO s_mt.t_class_attr" +
		"(ck_id, ck_class, ck_attr, cv_data_type_extra, cv_value, ck_user, ct_change, cl_required, cl_empty)" +
		" VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s) " +
		" on conflict on constraint cin_c_class_attr_1 do update set ck_class = excluded.ck_class, ck_attr = excluded.ck_attr, cv_data_type_extra = excluded.cv_data_type_extra, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change, cl_required = excluded.cl_required, cl_empty = excluded.cl_empty;"

data class MClassAttr(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_class") val ckClass: String,
	@ColumnName("ck_attr") val ckAttr: String,
	@ColumnName("cv_data_type_extra") val cvDataTypeExtra: String?,
	@ColumnName("cv_value") val cvValue: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp,
	@ColumnName("cl_required") val clRequired: Int,
	@ColumnName("cl_empty") val clEmpty: Int = 0
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMClassAttrSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckClass),
			getSqlStringOrNull(ckAttr),
			getSqlStringOrNull(cvDataTypeExtra),
			getSqlStringOrNull(cvValue),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange),
			clRequired,
			clEmpty
		);
	}
}