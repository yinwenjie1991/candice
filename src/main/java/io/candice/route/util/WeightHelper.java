package io.candice.route.util;

import io.candice.config.model.DataNodeConfig;
import io.candice.server.mysql.MySQLDataNode;

import java.util.Random;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-11-03
 */
public class WeightHelper {

    private static Random r = new Random();

    public static int getReadIndex(MySQLDataNode dataNode) {
        DataNodeConfig config = dataNode.getConfig();
        int idx = dataNode.getActivedIndex();
        int m = config.getMasterReadWeight() + config.getSlaveReadWeight();
        int k = (int) (r.nextDouble() * (double) m);
        boolean isSlave = k >= config.getMasterReadWeight();
        if (isSlave) {
            if (idx == 0) {
                k = 1;
            }
            if (idx == 1) {
                k = 2;
            }
            if (idx == 2) {
                k = 1;
            }
        }
        return k;

    }

    public static void main(String[] args) {
        DataNodeConfig config = new DataNodeConfig();
        config.setMasterReadWeight(9999);
        config.setSlaveReadWeight(1);
        int c = 0;
        int m = config.getMasterReadWeight() + config.getSlaveReadWeight();
        for (int i = 0; i < 10000; i++) {
            int k = (int) (r.nextDouble() * (double) m);
            boolean isSlave = k >= config.getMasterReadWeight();
            if (isSlave)
                c++;
        }
        System.out.print(c);

    }
}
