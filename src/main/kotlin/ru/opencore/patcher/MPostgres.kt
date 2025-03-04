package ru.opencore.patcher

class MPostgres : MDB {
    override val type: DBType = DBType.POSTGRES
    override val sqlMAttr: String = "select distinct\n" +
            "    a.ck_id,\n" +
            "    a.cv_description,\n" +
            "    a.ck_attr_type,\n" +
            "    a.ck_d_data_type,\n" +
            "    a.cv_data_type_extra,\n" +
            "    a.ck_user,\n" +
            "    a.ct_change\n" +
            "from\n" +
            "    s_mt.t_attr a\n" +
            "left join s_mt.t_class_attr ca\n" +
            " on a.ck_id = ca.ck_attr\n" +
            "left join s_mt.t_page_attr pa\n" +
            " on a.ck_id = pa.ck_attr\n" +
            "where 1=1 and (coalesce(ca.ck_id, pa.ck_id) is not null or a.ck_id = 'defaultvaluerule') <FILTER>\n" +
            "order by a.ck_id asc\n"
    override val sqlMAttrType = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cv_description,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_attr_type\n" +
            "order by ck_id asc\n"
    override val sqlMAttrDataType = "select\n" +
            "    ck_id,\n" +
            "    cv_description,\n" +
            "    cl_extra,\n" +
            "    ck_parent,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_d_attr_data_type\n" +
            "order by ck_id asc\n"
    override val sqlMClass: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cv_description,\n" +
            "    cv_manual_documentation,\n" +
            "    cv_auto_documentation,\n" +
            "    cl_final,\n" +
            "    cl_dataset,\n" +
            "    ck_view,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_class\n" +
            "where true <FILTER>\n" +
            "order by cv_name asc\n"
    override val sqlMClassAttr: String = "select\n" +
            "    ck_id,\n" +
            "    ck_class,\n" +
            "    ck_attr,\n" +
            "    cv_data_type_extra,\n" +
            "    cv_value,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    cl_required,\n" +
            "    cl_empty\n" +
            "from\n" +
            "    s_mt.t_class_attr where true <FILTER>\n" +
            "order by ck_attr asc\n"
    override val sqlMClassHierarchy: String = "select\n" +
            "    ck_id,\n" +
            "    ck_class_parent,\n" +
            "    ck_class_child,\n" +
            "    ck_class_attr,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_class_hierarchy\n" +
            "where true <FILTER>\n" +
            "order by ck_class_parent asc, ck_class_child asc\n"
    override val sqlMProvider: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_provider\n" +
            "order by ck_id asc\n"
    override val sqlMQuery: String = "select\n" +
            "    ck_id,\n" +
            "    cc_query,\n" +
            "    ck_provider,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    cr_type,\n" +
            "    cr_access,\n" +
            "    cn_action,\n" +
            "    cv_description,\n" +
            "    cr_cache,\n" +
            "    cv_cache_key_param\n" +
            "from\n" +
            "    s_mt.t_query\n" +
            "where true\n" +
            "    <FILTER> order by ck_id asc\n"
    override val sqlMObject: String = "with recursive obj as (\n" +
            "select\n" +
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
            "    o.ck_user,\n" +
            "    o.ct_change,\n" +
            "    1 as lvl\n" +
            "from\n" +
            "    s_mt.t_object o\n" +
            "where\n" +
            "    ck_id = :ckId\n" +
            "union all\n" +
            "select\n" +
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
            "    o.ck_user,\n" +
            "    o.ct_change,\n" +
            "    c.lvl+1 as lvl\n" +
            "from\n" +
            "    s_mt.t_object o\n" +
            "join obj c on\n" +
            "    o.ck_parent = c.ck_id )\n" +
            "select\n" +
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
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    obj\n" +
            "order by lvl asc, cn_order asc, cv_name asc\n"
    override val sqlMObjectAttr: String = "with recursive obj as (\n" +
            "select\n" +
            "    o.ck_id,\n" +
            "    o.ck_parent,\n" +
            "    1 as lvl\n" +
            "from\n" +
            "    s_mt.t_object o\n" +
            "where\n" +
            "    ck_id = :ckId\n" +
            "union all\n" +
            "select\n" +
            "    o.ck_id,\n" +
            "    o.ck_parent,\n" +
            "    c.lvl+1 as lvl\n" +
            "from\n" +
            "    s_mt.t_object o\n" +
            "join obj c on\n" +
            "    o.ck_parent = c.ck_id )\n" +
            "select\n" +
            "    attr.ck_id,\n" +
            "    attr.ck_object,\n" +
            "    attr.ck_class_attr,\n" +
            "    attr.cv_value,\n" +
            "    attr.ck_user,\n" +
            "    attr.ct_change,\n" +
            "    ca.ck_attr\n" +
            "from\n" +
            "    obj ob \n" +
            "join s_mt.t_object_attr attr\n" +
            "  on ob.ck_id = attr.ck_object\n" +
            "join s_mt.t_class_attr ca\n" +
            "  on ca.ck_id = attr.ck_class_attr\n" +
            "order by ob.lvl asc, ob.cn_order asc, ca.ck_id asc \n"
    override val sqlMView: String = "select\n" +
            "    ck_id,\n" +
            "    cv_description,\n" +
            "    cct_config,\n" +
            "    cct_manifest,\n" +
            "    cl_available,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_view where 1=1 <FILTER>\n" +
            "order by a.ck_id asc\n"
    override val sqlMPage: String = "with recursive page as (\n" +
            "select\n" +
            "    ck_id,\n" +
            "    ck_parent,\n" +
            "    cr_type,\n" +
            "    cv_name,\n" +
            "    cn_order,\n" +
            "    cl_static,\n" +
            "    cv_url,\n" +
            "    ck_icon,\n" +
            "    ck_view,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    cl_menu,\n" +
            "    cv_redirect_url,\n" +
            "    cl_multi,\n" +
            "    1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page\n" +
            "where\n" +
            "    ck_id = :ckPage\n" +
            "union all\n" +
            "select\n" +
            "    p.ck_id,\n" +
            "    p.ck_parent,\n" +
            "    p.cr_type,\n" +
            "    p.cv_name,\n" +
            "    p.cn_order,\n" +
            "    p.cl_static,\n" +
            "    p.cv_url,\n" +
            "    p.ck_icon,\n" +
            "    p.ck_view,\n" +
            "    p.ck_user,\n" +
            "    p.ct_change,\n" +
            "    p.cl_menu,\n" +
            "    p.cv_redirect_url,\n" +
            "    p.cl_multi,\n" +
            "    rp.lvl+1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page p\n" +
            "join page rp on\n" +
            "    p.ck_parent = rp.ck_id )\n" +
            "select\n" +
            "    ck_id,\n" +
            "    ck_parent,\n" +
            "    cr_type,\n" +
            "    cv_name,\n" +
            "    cn_order,\n" +
            "    cl_static,\n" +
            "    cv_url,\n" +
            "    ck_icon,\n" +
            "    ck_view,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    cl_menu,\n" +
            "    cv_redirect_url,\n" +
            "    cl_multi\n" +
            "from\n" +
            "    page\n" +
            "order by lvl asc, cn_order asc, cv_name asc\n"
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
    override val sqlMPageObject: String = "with recursive page_object as (\n" +
            "select\n" +
            "    po.ck_id,\n" +
            "    po.ck_page,\n" +
            "    po.ck_object,\n" +
            "    po.cn_order,\n" +
            "    po.ck_parent,\n" +
            "    po.ck_master,\n" +
            "    po.ck_user,\n" +
            "    po.ct_change,\n" +
            "    o.ck_parent as ck_parent_object,\n" +
            "    o.cv_name as cv_name_object,\n" +
            "    1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "join s_mt.t_object o\n" +
            "    on o.ck_id = po.ck_object\n" +
            "where\n" +
            "    po.ck_parent is null and\n" +
            "    po.ck_page = :ckPage\n" +
            "union all\n" +
            "select\n" +
            "    po.ck_id,\n" +
            "    po.ck_page,\n" +
            "    po.ck_object,\n" +
            "    po.cn_order,\n" +
            "    po.ck_parent,\n" +
            "    po.ck_master,\n" +
            "    po.ck_user,\n" +
            "    po.ct_change,\n" +
            "    o.ck_parent as ck_parent_object,\n" +
            "    o.cv_name as cv_name_object,\n" +
            "    rp.lvl+1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "join page_object rp on\n" +
            "    po.ck_parent = rp.ck_id\n" +
            "join s_mt.t_object o\n" +
            "    on o.ck_id = po.ck_object\n" +
            ")\n" +
            "select\n" +
            "    ck_id,\n" +
            "    ck_page,\n" +
            "    ck_object,\n" +
            "    cn_order,\n" +
            "    ck_parent,\n" +
            "    ck_master,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    ck_parent_object\n" +
            "from\n" +
            "    page_object\n" +
            "order by lvl asc, cn_order asc\n"
    override val sqlMPageObjectMaster: String = "select\n" +
            "    po.ck_id,\n" +
            "    po.ck_master\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "where\n" +
            "    po.ck_master is not null and po.ck_page = :ckPage\n" +
            "order by\n" +
            "    po.cn_order asc\n"
    override val sqlMPageObjectAttr: String = "with recursive page_object as (\n" +
            "select\n" +
            "    ck_id,\n" +
            "    cn_order,\n" +
            "    1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page_object\n" +
            "where\n" +
            "    ck_parent is null and\n" +
            "    ck_page = :ckPage\n" +
            "union all\n" +
            "select\n" +
            "    po.ck_id,\n" +
            "    po.cn_order,\n" +
            "    rp.lvl+1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "join page_object rp on\n" +
            "    po.ck_parent = rp.ck_id )\n" +
            "select\n" +
            "    attr.ck_id,\n" +
            "    attr.ck_page_object,\n" +
            "    attr.ck_class_attr,\n" +
            "    attr.cv_value,\n" +
            "    attr.ck_user,\n" +
            "    attr.ct_change,\n" +
            "    ca.ck_attr\n" +
            "from\n" +
            "    s_mt.t_page_object_attr attr\n" +
            "join page_object po on\n" +
            "    ck_page_object = po.ck_id\n" +
            "join s_mt.t_class_attr ca\n" +
            "on ca.ck_id = attr.ck_class_attr\n" +
            "order by po.lvl asc, po.cn_order asc, attr.ck_class_attr asc\n"
    override val sqlMPageVariable: String =
            "select\n" +
            "    vp.ck_id,\n" +
            "    vp.ck_page,\n" +
            "    vp.cv_name,\n" +
            "    vp.cv_description,\n" +
            "    vp.cv_value,\n" +
            "    vp.ck_user,\n" +
            "    vp.ct_change\n" +
            "from\n" +
            "    s_mt.t_page_variable vp\n" +
            "  where vp.ck_page = :ckPage\n" +
            "order by vp.cv_name asc\n"
    override val sqlMPageAction: String =
            "select\n" +
            "    ap.ck_id,\n" +
            "    ap.ck_page,\n" +
            "    ap.cr_type,\n" +
            "    ap.cn_action,\n" +
            "    ap.ck_user,\n" +
            "    ap.ct_change\n" +
            "from\n" +
            "    s_mt.t_page_action ap\n" +
            "  where ap.ck_page = :ckPage\n" +
            "order by ap.cr_type asc\n"
    override val sqlSysSettings: String = "select\n" +
            "    ck_id,\n" +
            "    cv_value,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    cv_description\n" +
            "from\n" +
            "    s_mt.t_sys_setting order by ck_id asc\n"
    override val sqlMessages: String = "select\n" +
            "    ck_id,\n" +
            "    cr_type,\n" +
            "    cv_text,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_message\n" +
            "where\n" +
            "    ck_id > 1000 order by ck_id asc\n"
    override val sqlLocalizationMessage: String = "select\n" +
            "    ck_id,\n" +
            "    ck_d_lang,\n" +
            "    cr_namespace,\n" +
            "    cv_value,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_localization\n" +
            " where cr_namespace = 'message'\n" +
            " order by ck_d_lang asc, ck_id asc\n"
    override val sqlModule: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    ck_user,\n" +
            "    ct_change,\n" +
            "    ck_view,\n" +
            "    cv_version,\n" +
            "    cv_version_api,\n" +
            "    cl_available,\n" +
            "    cc_manifest,\n" +
            "    cc_config\n" +
            "from\n" +
            "    s_mt.t_module order by cv_name asc\n"
    override val sqlModuleClass: String = "select\n" +
            "    mc.ck_module,\n" +
            "    mc.ck_class,\n" +
            "    mc.ck_user,\n" +
            "    mc.ct_change\n" +
            "from\n" +
            "    s_mt.t_module m\n" +
            "join s_mt.t_module_class mc\n" +
            "    on m.ck_id = mc.ck_module\n" +
            "where m.ck_id = :ckModule order by m.cv_name asc\n"
    override val sqlDlang: String = "select\n" +
            "    ck_id,\n" +
            "    cv_name,\n" +
            "    cl_default,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_d_lang\n" +
            "  order by ck_id asc\n"
    override val sqlLocalization: String = "select\n" +
            "    ck_id,\n" +
            "    ck_d_lang,\n" +
            "    cr_namespace,\n" +
            "    cv_value,\n" +
            "    ck_user,\n" +
            "    ct_change\n" +
            "from\n" +
            "    s_mt.t_localization\n" +
            "where\n" +
            "    ck_d_lang = :ckDLang\n" +
            "    and cr_namespace <> 'meta'\n" +
            "union all\n" +
            "select\n" +
            "    l.ck_id,\n" +
            "    l.ck_d_lang,\n" +
            "    l.cr_namespace,\n" +
            "    l.cv_value,\n" +
            "    l.ck_user,\n" +
            "    l.ct_change\n" +
            "from\n" +
            "    s_mt.t_localization l\n" +
            "where\n" +
            "    ck_d_lang = :ckDLang\n" +
            "    and cr_namespace = 'meta'\n" +
            "    and (\n" +
            "        :ckPage is null\n" +
            "        or (\n" +
            "            :ckPage is not null\n" +
            "            and l.ck_id in (\n" +
            "                with recursive page as (\n" +
            "                    select\n" +
            "                        p.ck_id,\n" +
            "                        p.ck_parent,\n" +
            "                        p.cv_name,\n" +
            "                        1 as lvl\n" +
            "                    from\n" +
            "                        s_mt.t_page p\n" +
            "                    where\n" +
            "                        p.ck_id in (\n" +
            "                            select\n" +
            "                                value::varchar\n" +
            "                            from\n" +
            "                                jsonb_array_elements_text(:ckPage::jsonb)\n" +
            "                        )\n" +
            "                union all\n" +
            "                    select\n" +
            "                        p.ck_id,\n" +
            "                        p.ck_parent,\n" +
            "                        p.cv_name,\n" +
            "                        rp.lvl + 1 as lvl\n" +
            "                    from\n" +
            "                        s_mt.t_page p\n" +
            "                    join page rp on\n" +
            "                        p.ck_parent = rp.ck_id\n" +
            "                )\n" +
            "                select\n" +
            "                    l.ck_id\n" +
            "                from\n" +
            "                    page p\n" +
            "                left join s_mt.t_page_object po on\n" +
            "                    p.ck_id = po.ck_page\n" +
            "                left join s_mt.t_object o on\n" +
            "                    po.ck_object = o.ck_id\n" +
            "                left join s_mt.t_page_object_attr po_attr on\n" +
            "                    po.ck_id = po_attr.ck_page_object\n" +
            "                left join s_mt.t_object_attr o_attr on\n" +
            "                    o.ck_id = o_attr.ck_object\n" +
            "                left join s_mt.t_class_attr ca on\n" +
            "                    o.ck_class = ca.ck_class\n" +
            "                left join s_mt.t_localization l on\n" +
            "                    l.ck_id = p.cv_name\n" +
            "                    or l.ck_id = o.cv_displayed\n" +
            "                    or l.ck_id = po_attr.cv_value\n" +
            "                    or l.ck_id = o_attr.cv_value\n" +
            "                    or l.ck_id = ca.cv_value\n" +
            "                where\n" +
            "                    l.ck_id is not null\n" +
            "            )\n" +
            "        )\n" +
            "    )\n" +
            "order by\n" +
            "    cr_namespace asc,\n" +
            "    ck_id asc\n"
    override val sqlMOriginObjectPage: String =
        "with recursive page_object as (\n" +
            "select\n" +
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
            "    o.ck_user,\n" +
            "    o.ct_change,\n" +
            "    po.ck_id as ck_page_object,\n" +
            "    1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "join s_mt.t_object o\n" +
            "    on o.ck_id = po.ck_object\n" +
            "where\n" +
            "    po.ck_parent is null and\n" +
            "    po.ck_page = :ckPage\n" +
            "union all\n" +
            "select\n" +
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
            "    o.ck_user,\n" +
            "    o.ct_change,\n" +
            "    po.ck_id as ck_page_object,\n" +
            "    rp.lvl+1 as lvl\n" +
            "from\n" +
            "    s_mt.t_page_object po\n" +
            "join page_object rp on\n" +
            "    po.ck_parent = rp.ck_page_object\n" +
            "join s_mt.t_object o\n" +
            "    on o.ck_id = po.ck_object\n" +
            ")\n" +
        "select\n" +
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
            "    ck_user,\n" +
            "    ct_change\n" +
            "from page_object\n" +
            "order by\n" +
            "    lvl asc, cn_order asc, cv_name asc\n"
    override val sqlMOriginObjectPageAttr: String = "select\n" +
            "    attr.ck_id,\n" +
            "    attr.ck_object,\n" +
            "    attr.ck_class_attr,\n" +
            "    attr.cv_value,\n" +
            "    attr.ck_user,\n" +
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
    override val sqlLocalizationPage: String = "with recursive page_object as (\n" +
            "    select\n" +
            "        po.ck_id,\n" +
            "        po.ck_page,\n" +
            "        po.ck_parent,\n" +
            "        po.ck_object,\n" +
            "        o.ck_class,\n" +
            "        o.cv_displayed,\n" +
            "        1 as lvl\n" +
            "    from\n" +
            "        s_mt.t_page_object po\n" +
            "    join s_mt.t_object o on\n" +
            "        po.ck_object = o.ck_id\n" +
            "    where\n" +
            "        po.ck_parent is null\n" +
            "        and po.ck_page = :ckPage\n" +
            "union all\n" +
            "    select\n" +
            "        po.ck_id,\n" +
            "        po.ck_page,\n" +
            "        po.ck_parent,\n" +
            "        po.ck_object,\n" +
            "        o.ck_class,\n" +
            "        o.cv_displayed,\n" +
            "        rp.lvl + 1 as lvl\n" +
            "    from\n" +
            "        s_mt.t_page_object po\n" +
            "    join s_mt.t_object o on\n" +
            "        po.ck_object = o.ck_id\n" +
            "    join page_object rp on\n" +
            "        po.ck_parent = rp.ck_id\n" +
            ")\n" +
            "select\n" +
            "    distinct l.*\n" +
            "from\n" +
            "    s_mt.t_page p\n" +
            "left join page_object po on\n" +
            "    p.ck_id = po.ck_page\n" +
            "left join s_mt.t_page_object_attr po_attr on\n" +
            "    po.ck_id = po_attr.ck_page_object\n" +
            "left join s_mt.t_object_attr o_attr on\n" +
            "    po.ck_object = o_attr.ck_object\n" +
            "left join s_mt.t_class_attr ca on\n" +
            "    po.ck_class = ca.ck_class\n" +
            "left join s_mt.t_localization l on\n" +
            "    l.ck_id = p.cv_name\n" +
            "    or l.ck_id = po.cv_displayed\n" +
            "    or l.ck_id = po_attr.cv_value\n" +
            "    or l.ck_id = o_attr.cv_value\n" +
            "    or l.ck_id = ca.cv_value\n" +
            "where\n" +
            "    l.ck_id is not null\n" +
            "    and p.ck_id = :ckPage\n" +
            "order by\n" +
            "    l.ck_d_lang asc,\n" +
            "    l.ck_id asc\n"
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
