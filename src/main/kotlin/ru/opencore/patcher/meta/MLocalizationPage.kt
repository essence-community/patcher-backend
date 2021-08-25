package ru.opencore.patcher.meta

import org.jdbi.v3.core.mapper.reflect.ColumnName
import java.util.stream.Collectors
import java.sql.Timestamp

var formatLocalizationPageSqlPostgres = "INSERT INTO s_mt.t_localization (ck_id, ck_d_lang, cr_namespace, cv_value, ck_user, ct_change)\n" +
		"select t.ck_id, t.ck_d_lang, t.cr_namespace, t.cv_value, t.ck_user, t.ct_change::timestamp from (\n" +
		"%s\n" +
		") as t \n" +
		" join s_mt.t_d_lang dl\n" +
		" on t.ck_d_lang = dl.ck_id\n" +
		"on conflict on constraint cin_u_localization_1 do update set ck_id = excluded.ck_id, ck_d_lang = excluded.ck_d_lang, cr_namespace = excluded.cr_namespace, cv_value = excluded.cv_value, ck_user = excluded.ck_user, ct_change = excluded.ct_change;"

var formaRowLocalizationSqlPostgres = "    select %s as ck_id, %s as ck_d_lang, %s as cr_namespace, %s as cv_value, %s as ck_user, %s as ct_change"


data class MLocalizationPage(
	val rows: List<MLocalization>
): Meta {
	fun getRow(): String {
		return this.rows.stream().map { row ->
			String.format(
		    formaRowLocalizationSqlPostgres,
			getSqlStringOrNull(row.ckId),
			getSqlStringOrNull(row.ckDLang),
			getSqlStringOrNull(row.crNamespace),
			getSqlStringOrNull(row.cvValue),
			getSqlStringOrNull(row.ckUser),
			getTimestampPostgres(row.ctChange)
				)
		}
		.collect(Collectors.joining("\n    union all\n"))
	}
	override fun toPostgres(): String {
		if (this.rows.isEmpty()) {
			return "";
		}
		return String.format(
			formatLocalizationPageSqlPostgres,
			this.getRow()
		);
	}
}