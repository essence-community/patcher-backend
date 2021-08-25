package ru.opencore.patcher.auth

import ru.opencore.patcher.meta.writeChangePostgres
import ru.opencore.patcher.DBType
import ru.opencore.patcher.MDB
import org.jdbi.v3.core.Handle
import ru.opencore.patcher.Config
import ru.opencore.patcher.meta.Meta
import java.util.ArrayList

fun createPatchAccount(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var listAll = ArrayList<Meta>()
	listAll.addAll(
		handle
			.createQuery(bdQuery.sqlAccount)
			.map({ rs, _ ->
				Account(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8),
					rs.getString(9),
					rs.getString(10),
					rs.getTimestamp(11)
				)
			})
			.list()
	)
	listAll.add(AccountInfoRows(handle
			.createQuery(bdQuery.sqlAccountInfo)
			.map({ rs, _ ->
				AccountInfo(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getTimestamp(6)
				)
			})
			.list()))
	listAll.add(AccountRoleRows(handle
			.createQuery(bdQuery.sqlAccountRole)
			.map({ rs, _ ->
				AccountRole(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getTimestamp(5)
				)
			})
			.list()))
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "Account", listAll)
	}
	return true
}

fun createPatchDInfo(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var listAll = ArrayList<Meta>()
	listAll.addAll(
		handle
			.createQuery(bdQuery.sqlDInfo)
			.map({ rs, _ ->
				DInfo(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getInt(4),
					rs.getString(5),
					rs.getTimestamp(6)
				)
			})
			.list()
	)
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "DInfo", listAll)
	}
	return true
}

fun createPatchAction(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var listAll = ArrayList<Meta>()
	listAll.addAll(
		handle
			.createQuery(bdQuery.sqlAction)
			.map({ rs, _ ->
				Action(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getTimestamp(5)
				)
			})
			.list()
	)
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "Action", listAll)
	}
	return true
}

fun createPatchRole(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var listAll = ArrayList<Meta>()
	listAll.addAll(
		handle
			.createQuery(bdQuery.sqlRole)
			.map({ rs, _ ->
				Role(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getTimestamp(5)
				)
			})
			.list()
	)
	listAll.add(RoleActionRows(handle
			.createQuery(bdQuery.sqlRoleAction)
			.map({ rs, _ ->
				RoleAction(
					rs.getString(1),
					rs.getInt(2),
					rs.getString(3),
					rs.getString(4),
					rs.getTimestamp(5)
				)
			})
			.list()))
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "Role", listAll)
	}
	return true
}