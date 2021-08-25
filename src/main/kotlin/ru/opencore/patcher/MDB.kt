package ru.opencore.patcher

import ru.opencore.patcher.DBType

interface MDB {
	val type: DBType
	val sqlMAttr: String
	val sqlMAttrType: String
	val sqlMAttrDataType: String
	val sqlMClass: String
	val sqlMClassAttr: String
	val sqlMClassHierarchy: String
	val sqlMProvider: String
	val sqlMQuery: String
	val sqlMObject: String
	val sqlMObjectAttr: String
	val sqlMView: String
	val sqlMPage: String
	val sqlMPageAttr: String
	val sqlMPageObject: String
	val sqlMPageObjectMaster: String
	val sqlMPageObjectAttr: String
	val sqlMPageVariable: String
	val sqlMPageAction: String
	val sqlSysSettings: String
	val sqlMessages: String
	val sqlLocalizationMessage: String
	val sqlModule: String
	val sqlModuleClass: String
	val sqlDlang: String
	val sqlLocalization: String
	val sqlMOriginObjectPage: String
	val sqlMOriginObjectPageAttr: String
	val sqlLocalizationPage: String
	val sqlAccount: String
	val sqlAccountRole: String
	val sqlAccountInfo: String
	val sqlDInfo: String
	val sqlRole: String
	val sqlAction: String
	val sqlRoleAction: String	
}