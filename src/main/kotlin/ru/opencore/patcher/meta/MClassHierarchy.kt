package ru.opencore.patcher.meta
		
import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp

var formatMClassHierarchySqlPostgres = "INSERT INTO s_mt.t_class_hierarchy" +
		"(ck_id, ck_class_parent, ck_class_child, ck_class_attr, ck_user, ct_change)" +
		" VALUES (%s, %s, %s, %s, %s, %s) " +
		" on conflict on constraint cin_c_class_hierarchy_1 do update set ck_class_parent = excluded.ck_class_parent, ck_class_child = excluded.ck_class_child, ck_class_attr = excluded.ck_class_attr, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

data class MClassHierarchy(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("ck_class_parent") val ckClassParent: String,
	@ColumnName("ck_class_child") val ckClassChild: String,
	@ColumnName("ck_class_attr") val ckClassAttr: String,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatMClassHierarchySqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(ckClassParent),
			getSqlStringOrNull(ckClassChild),
			getSqlStringOrNull(ckClassAttr),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}