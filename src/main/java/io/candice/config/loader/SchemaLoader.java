package io.candice.config.loader;

import io.candice.config.model.DataNodeConfig;
import io.candice.config.model.DataSourceConfig;
import io.candice.config.model.SchemaConfig;
import io.candice.config.model.TableRuleConfig;

import java.util.Map;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-15
 */
public interface SchemaLoader {

    Map<String, TableRuleConfig> getTableRules();

    Map<String, DataSourceConfig> getDataSources();

    Map<String, DataNodeConfig> getDataNodes();

    Map<String, SchemaConfig> getSchemas();
}
