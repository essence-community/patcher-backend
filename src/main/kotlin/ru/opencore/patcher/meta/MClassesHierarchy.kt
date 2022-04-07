package ru.opencore.patcher.meta
		
import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.sql.Timestamp
import java.util.stream.Collectors

var formatMClassHierarchySqlPostgresTop = "INSERT INTO s_mt.t_class_hierarchy\n" +
		"(ck_id, ck_class_parent, ck_class_child, ck_class_attr, ck_user, ct_change)\n" +
		" select t.ck_id, t.ck_class_parent, t.ck_class_child, t.ck_class_attr, t.ck_user, t.ct_change from (\n";
var formatMClassHierarchySqlPostgresBody = "    select %s as ck_id, %s as ck_class_parent, %s as ck_class_child, %s as ck_class_attr, %s as ck_user, %s::timestamp with time zone as ct_change\n"; 
var formatMClassHierarchySqlPostgresBotton = " ) as t\n" +
		" where t.ck_class_parent in (select ck_id from s_mt.t_class) and t.ck_class_child in (select ck_id from s_mt.t_class)\n" +
		" on conflict on constraint cin_c_class_hierarchy_1 do update set ck_class_parent = excluded.ck_class_parent, ck_class_child = excluded.ck_class_child, ck_class_attr = excluded.ck_class_attr, ck_user = excluded.ck_user, ct_change = excluded.ct_change;";

data class MClassesHierarchy(
	 val listClasses: List<MClassHierarchy>
): Meta {
	override fun toPostgres(): String {
		if (listClasses.isEmpty()) {
			return "";
		}
		var result = StringBuilder()
		result.append(formatMClassHierarchySqlPostgresTop)
		var list = listClasses.stream().map {value -> String.format(
			formatMClassHierarchySqlPostgresBody,
			getSqlStringOrNull(value.ckId),
			getSqlStringOrNull(value.ckClassParent),
			getSqlStringOrNull(value.ckClassChild),
			getSqlStringOrNull(value.ckClassAttr),
			getSqlStringOrNull(value.ckUser),
			getTimestampPostgres(value.ctChange)
		)}
		result.append(list.collect(Collectors.joining("    union all\n")))
		result.append(formatMClassHierarchySqlPostgresBotton)
		return result.toString()
	}
}