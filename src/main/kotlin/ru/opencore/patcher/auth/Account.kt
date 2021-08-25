package ru.opencore.patcher.auth

import java.sql.Timestamp
import org.jdbi.v3.core.mapper.reflect.ColumnName
import ru.opencore.patcher.meta.Meta
import ru.opencore.patcher.meta.getSqlStringOrNull
import ru.opencore.patcher.meta.getTimestampPostgres

var formatAccountSqlPostgres =
	"INSERT INTO s_at.t_account (ck_id, cv_surname, cv_name, cv_login, cv_hash_password, cv_timezone, cv_salt, cv_email, cv_patronymic, ck_user, ct_change) " +
    " VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) " +
    "on conflict (ck_id) do update set ck_id = excluded.ck_id, cv_name = excluded.cv_name, cv_surname = excluded.cv_surname, cv_login = excluded.cv_login, cv_hash_password = excluded.cv_hash_password, cv_timezone = excluded.cv_timezone, cv_salt = excluded.cv_salt, cv_email = excluded.cv_email, cv_patronymic = excluded.cv_patronymic, ck_user = excluded.ck_user, ct_change = excluded.ct_change;\n";

data class Account(
	@ColumnName("ck_id") val ckId: String,
	@ColumnName("cv_surname") val cvSurname: String,
	@ColumnName("cv_name") val cvName: String,
	@ColumnName("cv_login") val cvLogin: String,
	@ColumnName("cv_hash_password") val cvHashPassword: String,
	@ColumnName("cv_timezone") val cvTimezone: String? = "+03:00",
	@ColumnName("cv_salt") val cvSalt: String,
	@ColumnName("cv_email") val cvEmail: String?,
	@ColumnName("cv_patronymic") val cvPatronymic: String?,
	@ColumnName("ck_user") val ckUser: String,
	@ColumnName("ct_change") val ctChange: Timestamp
): Meta {
	override fun toPostgres(): String {
		return String.format(
			formatAccountSqlPostgres,
			getSqlStringOrNull(ckId),
			getSqlStringOrNull(cvSurname),
			getSqlStringOrNull(cvName),
			getSqlStringOrNull(cvLogin),
			getSqlStringOrNull(cvHashPassword),
			getSqlStringOrNull(cvTimezone),
			getSqlStringOrNull(cvSalt),
			getSqlStringOrNull(cvEmail),
			getSqlStringOrNull(cvPatronymic),
			getSqlStringOrNull(ckUser),
			getTimestampPostgres(ctChange)
		);
	}
}