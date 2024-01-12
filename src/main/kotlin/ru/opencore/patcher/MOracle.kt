package ru.opencore.patcher

class MOracle : MDB {
    override val type: DBType = DBType.ORACLE
    override val sqlMAttr: String = "select distinct\n" +
            "    a.ck_id,\n" +
            "    a.cv_description,\n" +
            "    a.ck_attr_type,\n" +
            "    'text' as ck_d_data_type,\n" +
            "    null as cv_data_type_extra,\n" +
            "    a.cn_user,\n" +
            "    a.ct_change\n" +
            "from\n" +
            "    s_mt.t_attr a\n" +
            "join s_mt.t_class_attr ca\n" +
            " on a.ck_id = ca.ck_attr\n" +
            "where 1=1 <FILTER>\n" +
            "order by a.ck_id asc\n"
    override val sqlMAttrType = "select\n" +
            "    null as ck_id,\n" +
            "    null as cv_name,\n" +
            "    null as cv_description,\n" +
            "    null as ck_user,\n" +
            "    null as ct_change\n" +
            "from dual where rownum = 0\n"
    override val sqlMAttrDataType = "select\n" +
            "    null as ck_id,\n" +
            "    null as cv_description,\n" +
            "    null as cl_extra,\n" +
            "    null as ck_parent,\n" +
            "    null as ck_user,\n" +
            "    null as ct_change\n" +
            "from dual where rownum = 0\n"
    override val sqlMClass: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cv_description,\n" +
            "    null as cv_manual_documentation,\n" +
            "    null as cv_auto_documentation,\n" +
            "    cl_final,\n" +
            "    cl_dataset,\n" +
            "    'system' as ck_view,\n" +
            "    cn_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_class\n" +
            "where 1=1 <FILTER>\n" +
            "order by cv_name asc\n"
    override val sqlMClassAttr: String = "select\n" +
            "    ck_id,\n" +
            "    ck_class,\n" +
            "    ck_attr,\n" +
            "    null as cv_data_type_extra,\n" +
            "    cv_value,\n" +
            "    cn_user,\n" +
            "    ct_change,\n" +
            "    cl_required,\n" +
            "    0 as cl_empty\n" +
            "from\n" +
            "    s_mt.t_class_attr where 1=1 <FILTER>\n" +
            "order by ck_attr asc\n"
    override val sqlMClassHierarchy: String = "select\n" +
            "    ck_id,\n" +
            "    ck_class_parent,\n" +
            "    ck_class_child,\n" +
            "    ck_class_attr,\n" +
            "    cn_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_class_hierarchy\n" +
            "where 1=1 <FILTER>\n" +
            "order by ct_change asc, ck_id asc\n"
    override val sqlMProvider: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cn_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_provider\n" +
            "order by ck_id asc\n"
    override val sqlMQuery: String = "select\n" +
            "    ck_id,\n" +
            "    cc_query,\n" +
            "    ck_provider,\n" +
            "    cn_user,\n" +
            "    ct_change,\n" +
            "    cr_type,\n" +
            "    cr_access,\n" +
            "    null as cn_action,\n" +
            "    'Необходимо актуализировать' as cv_description\n" +
            "from\n" +
            "    s_mt.t_query\n" +
            "where 1=1 \n" +
            "    <FILTER> order by ck_id asc\n"
    override val sqlMObject: String = "select\n" +
            "    ck_id,\n" +
            "    ck_class,\n" +
            "    ck_parent,\n" +
            "    cv_name,\n" +
            "    cn_order,\n" +
            "    ck_query,\n" +
            "    cv_description,\n" +
            "    cv_displayed,\n" +
            "    cv_modify,\n" +
            "    ck_provider,\n" +
            "    cn_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_object\n" +
            "start with\n" +
            "    ck_id = :ckId\n" +
            "connect by\n" +
            "    prior ck_id = ck_parent\n" +
            "order by\n" +
            "    level asc, cn_order asc, cv_name asc\n"
    override val sqlMObjectAttr: String = "select\n" +
            "    attr.ck_id,\n" +
            "    attr.ck_object,\n" +
            "    attr.ck_class_attr,\n" +
            "    attr.cv_value,\n" +
            "    attr.ck_user,\n" +
            "    attr.ct_change,\n" +
            "    ca.ck_attr\n" +
            "from\n" +
            "    (\n" +
            "    select\n" +
            "        ck_id,\n" +
            "        level as lvl\n" +
            "    from\n" +
            "        s_mt.t_object\n" +
            "    start with\n" +
            "        ck_id = :ckId\n" +
            "    connect by\n" +
            "        prior ck_id = ck_parent) ob\n" +
            "join s_mt.t_object_attr attr on\n" +
            "    attr.ck_object = ob.ck_id\n" +
            "join s_mt.t_class_attr ca\n" +
            "  on ca.ck_id = attr.ck_class_attr\n" +
            "order by\n" +
            "    ob.lvl asc, attr.ck_class_attr asc\n"
    override val sqlMView: String = "select\n" +
            "    ck_id,\n" +
            "    cv_description,\n" +
            "    cct_config,\n" +
            "    cct_manifest,\n" +
            "    cl_available,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_view where 1=1 <FILTER>\n"
    override val sqlMPage: String = "select\n" +
            "    ck_id,\n" +
            "    ck_parent,\n" +
            "    cr_type,\n" +
            "    cv_name,\n" +
            "    cn_order,\n" +
            "    cl_static,\n" +
            "    cv_url,\n" +
            "    ck_icon,\n" +
            "    'system' as ck_view,\n" +
            "    cn_user,\n" +
            "    ct_change,\n" +
            "    cl_menu,\n" +
            "    null as cv_redirect_url,\n" +
            "    0 as cl_multi\n" +
            "from\n" +
            "    s_mt.t_page\n" +
            "start with\n" +
            "    ck_id = :ckPage\n" +
            "connect by\n" +
            "    prior ck_id = ck_parent\n" +
            "order by\n" +
			"    lvl asc, cn_order asc, cv_name asc\n"
	override val sqlMPageAttr: String = "select\n" +
            "    pa.ck_id,\n" +
            "    pa.ck_page,\n" +
            "    pa.ck_attr,\n" +
            "    pa.cv_value,\n" +
            "    pa.ck_user,\n" +
            "    pa.ct_change\n" +
            "from\n" +
            "    s_mt.t_page_attr pa\n" +
            "where pa.ck_page = :ckPage\n" +
            "order by pa.ck_id\n"
    override val sqlMPageObject: String = "select\n" +
            "    po.ck_id,\n" +
            "    po.ck_page,\n" +
            "    po.ck_object,\n" +
            "    po.cn_order,\n" +
            "    po.ck_parent,\n" +
            "    po.ck_master,\n" +
            "    po.cn_user,\n" +
            "    po.ct_change,\n" +
            "    o.ck_parent as ck_parent_object\n" +
            "from\n" +
            "    (\n" +
            "    select\n" +
            "        cop.*,\n" +
            "        level as lvl\n" +
            "    from\n" +
            "        s_mt.t_page_object cop\n" +
            "    start with\n" +
            "        ck_parent is null and ck_page = :ckPage\n" +
            "    connect by\n" +
            "        prior ck_id = ck_parent) po\n" +
            "join s_mt.t_object o on\n" +
            "    o.ck_id = po.ck_object\n" +
            "order by\n" +
            "    po.lvl asc, po.cn_order asc, o.cv_name asc\n"
    override val sqlMPageObjectMaster: String = "select\n" +
            "    po.ck_id,\n" +
            "    po.ck_master\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "where\n" +
            "    po.ck_master is not null and po.ck_page = :ckPage\n" +
            "  order by po.cn_order asc\n"
    override val sqlMPageObjectAttr: String = "select\n" +
            "    attr.ck_id,\n" +
            "    attr.ck_page_object,\n" +
            "    attr.ck_class_attr,\n" +
            "    attr.cv_value,\n" +
            "    attr.cn_user,\n" +
            "    attr.ct_change,\n" +
            "    ca.ck_attr\n" +
            "from s_mt.t_page_object po\n" +
            "join s_mt.t_page_object_attr attr on\n" +
            "    attr.ck_page_object = po.ck_id\n" +
            "join s_mt.t_class_attr ca\n" +
            "  on ca.ck_id = attr.ck_class_attr\n" +
            "  where po.ck_page = :ckPage\n" +
            "order by\n" +
            "    po.cn_order asc, attr.ck_class_attr asc\n"
    override val sqlMPageVariable: String = "select\n" +
            "    vp.ck_id,\n" +
            "    vp.ck_page,\n" +
            "    vp.cv_name,\n" +
            "    vp.cv_description,\n" +
            "    null as cv_value,\n" +
            "    vp.cn_user,\n" +
            "    vp.ct_change\n" +
            "from\n" +
            "    s_mt.t_page_variable vp\n" +
            "  where vp.ck_page = :ckPage\n" +
            "    order by vp.cv_name asc, vp.ct_change asc\n"
    override val sqlMPageAction: String = "select\n" +
            "    ap.ck_id,\n" +
            "    ap.ck_page,\n" +
            "    ap.cr_type,\n" +
            "    ap.cn_action,\n" +
            "    ap.cn_user,\n" +
            "    ap.ct_change\n" +
            "from\n" +
            "    s_mt.t_page_action ap\n" +
            "  where ap.ck_page = :ckPage\n" +
            "    order by ap.cr_type asc\n"
    override val sqlSysSettings: String = "select\n" +
            "    ck_id,\n" +
            "    cv_value,\n" +
            "    cn_user,\n" +
            "    ct_change,\n" +
            "    cv_description\n" +
            "from\n" +
            "    s_mt.t_sys_setting order by ck_id asc\n"
    override val sqlMessages: String = "select\n" +
            "    ck_id,\n" +
            "    cr_type,\n" +
            "    cv_text,\n" +
            "    cn_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_message\n" +
            "where\n" +
            "    ck_id > 1000 order by ck_id asc\n"
    override val sqlLocalizationMessage: String = "select null as ck_id,\n" +
            "    null as ck_d_lang,\n" +
            "    null as cr_namespace,\n" +
            "    null as cv_value,\n" +
            "    null as ck_user,\n" +
            "    null as ct_change\n" +
            " from dual where rownum = 0"
    override val sqlModule: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cn_user,\n" +
            "    ct_change,\n" +
            "    'system' as ck_view,\n" +
            "    cv_version,\n" +
            "    cv_version as cv_version_api,\n" +
            "    cl_available,\n" +
            "    cc_manifest,\n" +
            "    '{}' as cc_manifest\n" +
            "from\n" +
            "    s_mt.t_module order by cv_name asc\n"
    override val sqlModuleClass: String = "select\n" +
            "    ck_id as ck_module,\n" +
            "    ck_class,\n" +
            "    cn_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_module where ck_id = :ckModule order by cv_name asc\n"
    override val sqlDlang: String = "select\n" +
            "    null as ck_id,\n" +
            "    null as cv_name,\n" +
            "    null as cl_default,\n" +
            "    null as ck_user,\n" +
            "    null as ct_change\n" +
            " from dual where rownum = 0"
    override val sqlLocalization: String = "select null as ck_id,\n" +
            "    null as ck_d_lang,\n" +
            "    null as cr_namespace,\n" +
            "    null as cv_value,\n" +
            "    null as ck_user,\n" +
            "    null as ct_change\n" +
            " from dual where rownum = 0 and 'lolo' = :ckDLang"
    override val sqlMOriginObjectPage: String = "select\n" +
            "    o.ck_id,\n" +
            "    o.ck_class,\n" +
            "    o.ck_parent,\n" +
            "    o.cv_name,\n" +
            "    o.cn_order,\n" +
            "    o.ck_query,\n" +
            "    o.cv_description,\n" +
            "    o.cv_displayed,\n" +
            "    o.cv_modify,\n" +
            "    o.ck_provider,\n" +
            "    o.cn_user,\n" +
            "    o.ct_change\n" +
            "from\n" +
            "    (\n" +
            "    select\n" +
            "        cop.*,\n" +
            "        level as lvl\n" +
            "    from\n" +
            "        s_mt.t_page_object cop\n" +
            "    start with\n" +
            "        ck_parent is null and ck_page = :ckPage\n" +
            "    connect by\n" +
            "        prior ck_id = ck_parent) po\n" +
            "join s_mt.t_object o on\n" +
            "    o.ck_id = po.ck_object\n" +
            "order by\n" +
            "    po.lvl asc, o.cn_order asc, o.cv_name asc\n"
    override val sqlMOriginObjectPageAttr: String = "select\n" +
            "    attr.ck_id,\n" +
            "    attr.ck_object,\n" +
            "    attr.ck_class_attr,\n" +
            "    attr.cv_value,\n" +
            "    attr.cn_user,\n" +
            "    attr.ct_change,\n" +
            "    ca.ck_attr\n" +
            "from s_mt.t_page_object po\n" +
            "join s_mt.t_object_attr attr on\n" +
            "    attr.ck_object = po.ck_object\n" +
            "join s_mt.t_class_attr ca\n" +
            "  on ca.ck_id = attr.ck_class_attr\n" +
            "  where po.ck_page = :ckPage\n" +
            "order by\n" +
            "    po.cn_order asc, attr.ck_class_attr asc\n"
    override val sqlLocalizationPage: String = "select null as ck_id,\n" +
            "    null as ck_d_lang,\n" +
            "    null as cr_namespace,\n" +
            "    null as cv_value,\n" +
            "    null as ck_user,\n" +
            "    null as ct_change\n" +
            " from dual where rownum = 0 and 'test_lol' = :ckPage"
    override val sqlAccount =
            "select\n" +
            "    ck_id,\n" +
            "    cv_surname,\n" +
            "    cv_name,\n" +
            "    cv_login,\n" +
            "    cv_hash_password,\n" +
            "    cv_timezone,\n" +
            "    cv_salt,\n" +
            "    cv_email,\n" +
            "    cv_patronymic,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_account\n" +
            "order by\n" +
            "    cv_login asc\n"
    override val sqlAccountInfo =
            "select\n" +
            "    ck_id,\n" +
            "    ck_account,\n" +
            "    ck_d_info,\n" +
            "    cv_value,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_account_info\n" +
            "order by\n" +
            "    ck_d_info asc\n"
    override val sqlAccountRole =
            "select\n" +
            "    ck_id,\n" +
            "    ck_role,\n" +
            "    ck_account,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_account_role\n" +
            "order by\n" +
            "    ck_id asc\n"

    override val sqlAction =
            "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cv_description,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_action\n" +
            "order by\n" +
            "    ck_id asc\n"
    override val sqlDInfo =
            "select\n" +
            "    ck_id,\n" +
            "    cv_description,\n" +
            "    cr_type,\n" +
            "    cl_required,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_d_info\n" +
            "order by\n" +
            "    ck_id asc\n"
    override val sqlRole =
            "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cv_description,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_role\n" +
            "order by\n" +
            "    cv_name asc\n"
    override val sqlRoleAction =
            "select\n" +
            "    ck_id,\n" +
            "    ck_action,\n" +
            "    ck_role,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_at.t_role_action\n" +
            "order by\n" +
            "    ck_id asc\n"
    override val sqlIcon =
            "select\n" + 
            "    ck_id,\n" + 
            "    cv_name,\n" + 
            "    cv_font,\n" + 
            "    ck_user,\n" + 
            "    ct_change\n" + 
            "from\n" + 
            "    s_mt.t_icon\n" + 
            "order by cv_font asc, cv_name asc\n"
}
