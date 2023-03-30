package ru.opencore.patcher.meta

class MAttrClean: Meta {
	override fun toPostgres(): String {
		return "delete " + 
			"from " + 
			"s_mt.t_attr a " + 
			"where " + 
			"a.ck_id not in (" + 
			"select " + 
			"ck_attr " + 
			"from " + 
			"s_mt.t_class_attr) and a.ck_id not in ('defaultvalue','activerules','redirecturl','defaultvaluerule','titlerules');";
	}
}