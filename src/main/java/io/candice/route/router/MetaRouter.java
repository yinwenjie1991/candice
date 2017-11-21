package io.candice.route.router;

import io.candice.config.model.SchemaConfig;
import io.candice.config.model.TableConfig;
import io.candice.parser.ast.stmt.SQLStatement;
import io.candice.parser.visitor.MySQLOutputASTVisitor;
import io.candice.route.RouteResultset;
import io.candice.route.RouteResultsetNode;
import io.candice.route.visitor.PartitionKeyVisitor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MetaRouter {

    public static void routeForTableMeta(RouteResultset rrs, SchemaConfig schema, SQLStatement ast,
                                         PartitionKeyVisitor visitor, String stmt) {
        String sql = stmt;
        if (visitor.isSchemaTrimmed()) {
            sql = genSQL(ast, stmt);
        }
        String[] tables = visitor.getMetaReadTable();
        if (tables == null) {
            throw new IllegalArgumentException("route err: tables[] is null for meta read table: "
                                               + stmt);
        }
        String[] dataNodes;
        if (tables.length <= 0) {
            dataNodes = schema.getMetaDataNodes();
        } else if (tables.length == 1) {
            dataNodes = new String[1];
            dataNodes[0] = getMetaReadDataNode(schema, tables[0]);
        } else {
            Set<String> dataNodeSet = new HashSet<String>(tables.length, 1);
            for (String table : tables) {
                String dataNode = getMetaReadDataNode(schema, table);
                dataNodeSet.add(dataNode);
            }
            dataNodes = new String[dataNodeSet.size()];
            Iterator<String> iter = dataNodeSet.iterator();
            for (int i = 0; i < dataNodes.length; ++i) {
                dataNodes[i] = iter.next();
            }
        }

        RouteResultsetNode[] nodes = new RouteResultsetNode[dataNodes.length];
        rrs.setNodes(nodes);
        for (int i = 0; i < dataNodes.length; ++i) {
            nodes[i] = new RouteResultsetNode(dataNodes[i], new String[] { sql });
        }
    }

    private static String getMetaReadDataNode(SchemaConfig schema, String table) {
        String dataNode = schema.getDataNode();
        Map<String, TableConfig> tables = schema.getTables();
        TableConfig tc;
        if (tables != null && (tc = tables.get(table)) != null) {
            String[] dn = tc.getDataNodes();
            if (dn != null && dn.length > 0) {
                dataNode = dn[0];
            }
        }
        return dataNode;
    }

    private static String genSQL(SQLStatement ast, String orginalSql) {
        StringBuilder s = new StringBuilder();
        ast.accept(new MySQLOutputASTVisitor(s));
        return s.toString();
    }

}
