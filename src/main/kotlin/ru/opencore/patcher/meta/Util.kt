package ru.opencore.patcher.meta

import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.HashMap
import java.util.stream.Collectors
import org.jdbi.v3.core.Handle
import ru.opencore.patcher.Config
import ru.opencore.patcher.DBType
import ru.opencore.patcher.MDB

var dfPostgres = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

fun getTimestampPostgres(date: Date): String {
	return "'" + dfPostgres.format(date) + "'"
}

fun getTimestampPostgres(date: java.sql.Date): String {
	return "'" + dfPostgres.format(date) + "'"
}

fun getSqlStringOrNull(str: String?): String {
	if (str == null || str.isEmpty()) {
		return "null"
	}
	return "'" + str.replace("'", "''") + "'"
}

fun createClassPatch(handle: Handle, config: Config, bdQuery: MDB, isOnlyAttr: Boolean): Boolean {
	var listAll = ArrayList<Meta>()
	var nameTableModuleClass = if (bdQuery.type == DBType.ORACLE) "s_mt.t_module" else "s_mt.t_module_class"
	if (config.ckIds.isEmpty() || isOnlyAttr) {
		listAll.addAll(
			handle
				.createQuery(bdQuery.sqlMAttrType)
				.map({ rs, _ ->
					MAttrType(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getTimestamp(5)
					)
				})
				.list()
		)
		listAll.addAll(
			handle
				.createQuery(bdQuery.sqlMAttrDataType)
				.map({ rs, _ ->
					MAttrDataType(
						rs.getString(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
					)
				})
				.list()
		)
		listAll.addAll(
			handle
				.createQuery(bdQuery.sqlMAttr)
				.define(
					"FILTER", "and a.ck_id not in (select\n" +
							"    distinct a.ck_id\n" +
							"from\n" +
							"    s_mt.t_attr a\n" +
							"join s_mt.t_class_attr ac on\n" +
							"    a.ck_id = ac.ck_attr\n" +
							"join $nameTableModuleClass mc on\n" +
							"    ac.ck_class = mc.ck_class\n" +
							"left join s_mt.t_class_attr nac on\n" +
							"    a.ck_id = nac.ck_attr\n" +
							"    and nac.ck_class != mc.ck_class\n" +
							"where\n" +
							"    nac.ck_id is null)\n"
				)
				.map({ rs, _ ->
					MAttr(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getTimestamp(7)
					)
				})
				.list()
		)
		if (config.type == DBType.POSTGRES) {
			writeChangePostgres(config.patchDir, "classes", listAll)
		}
		if (isOnlyAttr) {
			sortXmlClasses("${config.patchDir}/classes")
			sortXmlClassesHierarchy("${config.patchDir}/classes_hierarchy")
			return true
		}
		handle
			.createQuery(bdQuery.sqlMClass)
			.define("FILTER", "and ck_id not in (select mc.ck_class from $nameTableModuleClass mc)")
			.map({ rs, _ ->
				MClass(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getInt(6),
					rs.getInt(7),
					rs.getString(8),
					rs.getString(9),
					rs.getTimestamp(10)
				)
			})
			.forEach { cl ->
				var metaClass = ArrayList<Meta>()
				metaClass.add(cl)
				var clAttr = handle
					.createQuery(bdQuery.sqlMClassAttr)
					.define("FILTER", "and ck_class = :ck_class")
					.bind("ck_class", cl.ckId)
					.map({ rs, _ ->
						MClassAttr(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getTimestamp(7),
							rs.getInt(8),
							rs.getInt(9)
						)
					})
					.list()
				var attrs = clAttr
					.stream()
					.map { row -> row.ckAttr }
					.collect(Collectors.toList())
				metaClass.addAll(clAttr)
				metaClass.add(MCleanObjectAttr(cl.ckId, attrs))
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres("${config.patchDir}/classes", "MetaClass_${cl.ckId}", metaClass)
				}
				var metaClassHierarchyList = handle
				.createQuery(bdQuery.sqlMClassHierarchy)
				.define(
					"FILTER",
					"and (ck_class_parent = :ckId or ck_class_child = :ckId) and ck_class_parent not in (select mc.ck_class from $nameTableModuleClass mc) and ck_class_child not in (select mc.ck_class from $nameTableModuleClass mc)"
				)
				.bind("ckId", cl.ckId)
				.map({ rs, _ ->
					MClassHierarchy(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
					)
				})
				.list()
				var metaClassesHierarchy = ArrayList<Meta>()
				if (!metaClassHierarchyList.isEmpty()) {
					metaClassesHierarchy.add(
						MClassesHierarchy(metaClassHierarchyList)
					)
				}
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres("${config.patchDir}/classes_hierarchy", "MetaClassHierarchy_${cl.ckId}", metaClassesHierarchy)
				}			
			}
		var metaClassesClean = ArrayList<Meta>()
		metaClassesClean.add(MAttrClean())
		if (config.type == DBType.POSTGRES) {
			writeChangePostgres(config.patchDir, "classes_clean", metaClassesClean)
		}	
	} else {
		for (objId in config.ckIds) {
			handle
				.createQuery(bdQuery.sqlMClass)
				.define(
					"FILTER",
					"and (ck_id = :ckId or ck_id in (select ck_class from s_mt.t_class_attr where ck_attr = 'type' and cv_value = :ckId))"
				)
				.bind("ckId", objId)
				.map({ rs, _ ->
					MClass(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getInt(6),
						rs.getInt(7),
						rs.getString(8),
						rs.getString(9),
						rs.getTimestamp(10)
					)
				})
				.forEach { cl ->
					var metaClass = ArrayList<Meta>()
				metaClass.add(cl)
				var clAttr = handle
					.createQuery(bdQuery.sqlMClassAttr)
					.define("FILTER", "and ck_class = :ck_class")
					.bind("ck_class", cl.ckId)
					.map({ rs, _ ->
						MClassAttr(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getTimestamp(7),
							rs.getInt(8),
							rs.getInt(9)
						)
					})
					.list()
				var attrs = clAttr
					.stream()
					.map { row -> row.ckAttr }
					.collect(Collectors.toList())
				metaClass.addAll(clAttr)
				metaClass.add(MCleanObjectAttr(cl.ckId, attrs))
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres("${config.patchDir}/classes", "MetaClass_${cl.ckId}", metaClass)
				}
				var metaClassHierarchyList = handle
				.createQuery(bdQuery.sqlMClassHierarchy)
				.define(
					"FILTER",
					"and (ck_class_parent = :ckId or ck_class_child = :ckId) and ck_class_parent not in (select mc.ck_class from $nameTableModuleClass mc) and ck_class_child not in (select mc.ck_class from $nameTableModuleClass mc)"
				)
				.bind("ckId", cl.ckId)
				.map({ rs, _ ->
					MClassHierarchy(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
					)
				})
				.list()
				var metaClassesHierarchy = ArrayList<Meta>()
				if (!metaClassHierarchyList.isEmpty()) {
					metaClassesHierarchy.add(
						MClassesHierarchy(metaClassHierarchyList)
					)
				}
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres("${config.patchDir}/classes_hierarchy", "MetaClassHierarchy_${cl.ckId}", metaClassesHierarchy)
				}		
			}
		}
	}
	sortXmlClasses("${config.patchDir}/classes")
	sortXmlClassesHierarchy("${config.patchDir}/classes_hierarchy")
	return true
}

fun createQueryPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {

	var excludeProvider = listOf("meta", "admingate", "authcore")
	if (config.ckIds.isEmpty()) {
		handle
			.createQuery(bdQuery.sqlMProvider)
			.map({ rs, _ ->
				MProvider(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getTimestamp(4)
				)
			})
			.list().stream()
			.filter { provider ->
				(config.providers.isEmpty() && !excludeProvider.contains(provider.ckId)) || config.providers.contains(
					provider.ckId
				)
			}
			.forEach { provider ->
				var listAll = ArrayList<Meta>()
				listAll.add(provider)
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres("${config.patchDir}/query", "Provider_${provider.ckId}", listAll)
				}
				handle
					.createQuery(bdQuery.sqlMQuery)
					.define(
						"FILTER",
						"and ck_provider = :ckProvider"
					)
					.bind("ckProvider", provider.ckId)
					.map({ rs, _ ->
						MQuery(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getTimestamp(5),
							rs.getString(6),
							rs.getString(7),
							rs.getString(8),
							rs.getString(9),
							rs.getString(10),
							rs.getString(11)
						)
					})
					.stream()
					.forEach { query ->
						var listQuery = ArrayList<Meta>()
						listQuery.add(query)
						if (config.type == DBType.POSTGRES) {
							writeChangePostgres("${config.patchDir}/query", "${query.ckId}", listQuery)
						}
					}
			}
	} else {
		for (objId in config.ckIds) {
			handle
				.createQuery(bdQuery.sqlMQuery)
				.define(
					"FILTER",
					"and ck_id = :ckId"
				)
				.bind("ckId", objId)
				.map({ rs, _ ->
					MQuery(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getTimestamp(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11)
					)
				})
				.stream()
				.forEach { query ->
					var listQuery = ArrayList<Meta>()
					listQuery.add(query)
					if (config.type == DBType.POSTGRES) {
						writeChangePostgres("${config.patchDir}/query", "${query.ckId}", listQuery)
					}
				}
		}
	}
	sortXmlQuery("${config.patchDir}/query")
	return true
}

fun createSysSettingsPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var excludes = listOf("core_db_commit", "core_db_deployment_date", "core_db_major_version", "core_db_patch")
	var listAll = ArrayList<Meta>()
	listAll.addAll(handle
		.createQuery(bdQuery.sqlSysSettings)
		.map({ rs, _ ->
			MSysSetting(
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getTimestamp(4),
				rs.getString(5)
			)
		})
		.list().stream()
		.filter { provider ->
			(config.ckIds.isEmpty() && !excludes.contains(provider.ckId)) || config.ckIds.contains(
				provider.ckId
			)
		}
		.collect(Collectors.toList())
	)
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "MSysSettings", listAll)
	}
	return true
}

fun createIconPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var listAll = ArrayList<Meta>()
	listAll.addAll(handle
		.createQuery(bdQuery.sqlIcon)
		.map({ rs, _ ->
			MIcon(
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getTimestamp(5)
			)
		})
		.list().stream()
		.collect(Collectors.toList())
	)
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "icon", listAll)
	}
	return true
}

fun createModulesPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var nameTableModuleClass = if (bdQuery.type == DBType.ORACLE) "s_mt.t_module" else "s_mt.t_module_class"
	var nameColumnModuleClass = if (bdQuery.type == DBType.ORACLE) "ck_id" else "ck_module"
	handle
		.createQuery(bdQuery.sqlModule)
		.map({ rs, _ ->
			MModule(
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getTimestamp(4),
				rs.getString(5),
				rs.getString(6),
				rs.getString(7),
				rs.getInt(8),
				rs.getString(9),
				rs.getString(10)
			)
		})
		.stream()
		.filter { module -> config.ckIds.isEmpty() || config.ckIds.contains(module.ckId) }
		.forEach { module ->
			var listAll = ArrayList<Meta>()
			var arrClassM = ArrayList<Meta>()
			var moduleClass = HashMap<String, ArrayList<Meta>>()
			var moduleClassH = HashMap<String, ArrayList<Meta>>()
			listAll.add(module)
			listAll.addAll(
				handle
					.createQuery(bdQuery.sqlMAttr)
					.define(
						"FILTER", "and a.ck_id in (select\n" +
								"    distinct a.ck_id\n" +
								"from\n" +
								"    s_mt.t_attr a\n" +
								"join s_mt.t_class_attr ac on\n" +
								"    a.ck_id = ac.ck_attr\n" +
								"join $nameTableModuleClass mc on\n" +
								"    ac.ck_class = mc.ck_class\n" +
								"left join s_mt.t_class_attr nac on\n" +
								"    a.ck_id = nac.ck_attr\n" +
								"    and nac.ck_class != mc.ck_class\n" +
								"where\n" +
								"    nac.ck_id is null and mc.$nameColumnModuleClass = :ckModule)\n"
					)
					.bind("ckModule", module.ckId)
					.map({ rs, _ ->
						MAttr(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getString(5),
							rs.getString(6),
							rs.getTimestamp(7)
						)
					})
					.list()
			)
			handle
				.createQuery(bdQuery.sqlModuleClass)
				.bind("ckModule", module.ckId)
				.map { rs, _ ->
					MModuleClass(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getTimestamp(4)
					)
				}
				.forEach { mClass ->
					var cArr = ArrayList<Meta>()
					moduleClass.put(mClass.ckClass, cArr)
					cArr.addAll(
						handle
							.createQuery(bdQuery.sqlMClass)
							.define("FILTER", "and ck_id = :ck_class")
							.bind("ck_class", mClass.ckClass)
							.map({ rs, _ ->
								MClass(
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getInt(6),
									rs.getInt(7),
									rs.getString(8),
									rs.getString(9),
									rs.getTimestamp(10)
								)
							}).list()
					)
					var clAttr = handle
						.createQuery(bdQuery.sqlMClassAttr)
						.define("FILTER", "and ck_class = :ck_class")
						.bind("ck_class", mClass.ckClass)
						.map({ rs, _ ->
							MClassAttr(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getTimestamp(7),
								rs.getInt(8),
								rs.getInt(9)
							)
						})
						.list()
					var attrs = clAttr
						.stream()
						.map { row -> row.ckAttr }
						.collect(Collectors.toList())
					cArr.addAll(clAttr)
					cArr.add(MCleanObjectAttr(mClass.ckClass, attrs))
					var hArr = ArrayList<Meta>()
					moduleClassH.put(mClass.ckClass, hArr)
					hArr.addAll(
						handle
							.createQuery(bdQuery.sqlMClassHierarchy)
							.define("FILTER", "and ck_class_parent = :ck_class or ck_class_child = :ck_class")
							.bind("ck_class", mClass.ckClass)
							.map({ rs, _ ->
								MClassHierarchy(
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getTimestamp(6)
								)
							})
							.list()
					)
					arrClassM.add(mClass)
				}
			for (value in moduleClass.values) {
				listAll.addAll(value)
			}
			for (value in moduleClassH.values) {
				listAll.addAll(value)
			}
			listAll.addAll(arrClassM)
			if (config.type == DBType.POSTGRES) {
				writeChangePostgres(config.patchDir, "Module_${module.cvName}", listAll)
			}
		}
	return true
}

fun createMessagesPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var listAll = ArrayList<Meta>()
	listAll.addAll(handle
		.createQuery(bdQuery.sqlMessages)
		.map({ rs, _ ->
			MMessage(
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getTimestamp(5)
			)
		})
		.list().stream()
		.filter { provider -> config.ckIds.isEmpty() || config.ckIds.contains(provider.ckId) }
		.collect(Collectors.toList())
	)
	listAll.add(
		MLocalizationPage(
			handle
				.createQuery(
					bdQuery.sqlLocalizationMessage
				)
				.map({ rs, _ ->
					MLocalization(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
					)
				})
				.list()
		)
	)
	if (config.type == DBType.POSTGRES) {
		writeChangePostgres(config.patchDir, "MMessages", listAll)
	}
	return true
}

fun createPagePatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	for (objId in config.ckIds) {
		handle
			.createQuery(
				bdQuery.sqlMPage
			)
			.bind("ckPage", objId)
			.map({ rs, _ ->
				MPage(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getInt(6),
					rs.getString(7),
					rs.getString(8),
					rs.getString(9),
					rs.getString(10),
					rs.getTimestamp(11),
					rs.getInt(12),
					rs.getString(13),
					rs.getInt(14)
				)
			})
			.forEach { page ->
				var listAll = ArrayList<Meta>()

				listAll.add(MPageClean(page.ckId))

				listAll.add(MPageAttrDelete(page.ckId))
				listAll.add(page)

				listAll.addAll(
						handle
							.createQuery(
								bdQuery.sqlMPageAttr
							)
							.bind("ckPage", page.ckId)
							.map({ rs, _ ->
								MPageAttr(
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getTimestamp(6)
								)
							})
							.list()
					)

				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMPageAction
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MPageAction(
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
				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMPageVariable
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MPageVariable(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getTimestamp(7)
							)
						})
						.list()
				)
				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMOriginObjectPage
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MObject(
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
								rs.getString(11),
								rs.getTimestamp(12)
							)
						})
						.list()
				)
				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMOriginObjectPageAttr
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MObjectAttr(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getTimestamp(6),
								rs.getString(7)
							)
						})
						.list()
				)
				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMPageObject
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MPageObject(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getString(7),
								rs.getTimestamp(8),
								rs.getString(9)
							)
						})
						.list()
				)
				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMPageObjectAttr
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MPageObjectAttr(
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getTimestamp(6),
								rs.getString(7)
							)
						})
						.list()
				)
				listAll.addAll(
					handle
						.createQuery(
							bdQuery.sqlMPageObjectMaster
						)
						.bind("ckPage", page.ckId)
						.map({ rs, _ ->
							MPageObjectMaster(
								rs.getString(1),
								rs.getString(2)
							)
						})
						.list()
				)
				listAll.add(
					MLocalizationPage(
						handle
							.createQuery(
								bdQuery.sqlLocalizationPage
							)
							.bind("ckPage", page.ckId)
							.map({ rs, _ ->
								MLocalization(
									rs.getString(1),
									rs.getString(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5),
									rs.getTimestamp(6)
								)
							})
							.list()
					)
				)
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres("${config.patchDir}/page", "Page_" + page.ckId, listAll)
				}
			}
	}
	sortXmlPage("${config.patchDir}/page")
	return true
}

fun createObjectPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	for (objId in config.ckIds) {
		var listAll = ArrayList<Meta>()
		listAll.addAll(
			handle
				.createQuery(
					bdQuery.sqlMObject
				)
				.bind("ckId", objId)
				.map({ rs, _ ->
					MObject(
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
						rs.getString(11),
						rs.getTimestamp(12)
					)
				})
				.list()
		)
		listAll.addAll(
			handle
				.createQuery(
					bdQuery.sqlMObjectAttr
				)
				.bind("ckId", objId)
				.map({ rs, _ ->
					MObjectAttr(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6),
						rs.getString(7)
					)
				})
				.list()
		)
		if (config.type == DBType.POSTGRES) {
			writeChangePostgres(config.patchDir, "Object_" + objId, listAll)
		}
	}
	return true
}

fun createViewPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	if (config.ckIds.isEmpty()) {
		handle
			.createQuery(
				bdQuery.sqlMView
			)
			.define("FILTER", "")
			.map({ rs, _ ->
				MView(
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getInt(5),
					rs.getString(6),
					rs.getTimestamp(7)
				)
			})
			.forEach { view ->
				var listAll = ArrayList<Meta>()
				listAll.add(view)
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres(config.patchDir, "View_${view.ckId}", listAll)
				}
			}
	} else {
		for (objId in config.ckIds) {
			handle
				.createQuery(
					bdQuery.sqlMView
				)
				.define("FILTER", " and ck_id = :ckId")
				.bind("ckId", objId)
				.map({ rs, _ ->
					MView(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getString(6),
						rs.getTimestamp(7)
					)
				})
				.forEach { view ->
					var listAll = ArrayList<Meta>()
					listAll.add(view)
					if (config.type == DBType.POSTGRES) {
						writeChangePostgres(config.patchDir, "View_${view.ckId}", listAll)
					}
				}
		}
	}
	return true
}

fun createLangPatch(handle: Handle, config: Config, bdQuery: MDB): Boolean {
	var page = if (config.page.isEmpty()) {
		null
	} else {
		config.page.stream().map({ p ->
			"\"${p}\""
		}).collect(Collectors.joining(",", "[", "]"))
	}
	handle
		.createQuery(bdQuery.sqlDlang)
		.map({ rs, _ ->
			MDLang(
				rs.getString(1),
				rs.getString(2),
				rs.getInt(3),
				rs.getString(4),
				rs.getTimestamp(5)
			)
		})
		.list().stream()
		.filter { lang ->
			(config.ckIds.isEmpty() || config.ckIds.contains(
				lang.ckId
			))
		}
		.forEach { lang ->

			var allLocalization = handle
				.createQuery(bdQuery.sqlLocalization)
				.bind("ckDLang", lang.ckId)
				.bind("ckPage", page)
				.map({ rs, _ ->
					MLocalization(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
					)
				}).list()
			var nameSpaces = allLocalization.stream().map({ loc ->
				loc.crNamespace
			}).collect(Collectors.toSet())
			for (nameSpace in nameSpaces) {
				var listAll = ArrayList<Meta>()
				listAll.add(lang)
				listAll.addAll(
					allLocalization.stream()
						.filter({ loc ->
							loc.crNamespace.equals(nameSpace)
						}).collect(Collectors.toList())
				)
				if (config.type == DBType.POSTGRES) {
					writeChangePostgres(
						"${config.patchDir}/localization",
						"Localization_${nameSpace}_${lang.ckId}",
						listAll
					)
				}
			}
		}
	sortXmlLocalization("${config.patchDir}/localization")
	return true
}

fun writeChangePostgres(patchDir: String, name: String, rows: List<Meta>) {
	var file = File(patchDir + "/" + name + ".sql")
	if (!file.getParentFile().exists()) {
		file.getParentFile().mkdirs()
	}
	file.createNewFile()
	var writer = FileWriter(file)
	writer.append("--liquibase formatted sql\n")
	if (!rows.isEmpty()) {
		writer.append(
			String.format(
				"--changeset patcher-core:%s dbms:postgresql runOnChange:true splitStatements:false stripComments:false\n",
				name
			)
		)
	}
	writer.flush()
	var index = 0
	for (row in rows) {
		writer.append(row.toPostgres())
		writer.append("\n")
		index += 1
		if (index % 10 == 0) writer.flush()
	}
	writer.flush()
	writer.close()
}
