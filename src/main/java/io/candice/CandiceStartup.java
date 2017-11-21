package io.candice;

import io.candice.route.util.StringUtil;
import org.apache.log4j.helpers.LogLog;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述: candice-server启动类
 * 作者: yinwenjie
 * 日期: 2017-09-14
 */
public class CandiceStartup {

    private static final String dateFormat  = "yyyy-MM-dd HH:mm:ss";

    private static Map<String, String> PARAM_MAP = new HashMap<String, String>();

    private static final String CONFIG_PATH = "-conf";

    public static boolean hasSelfConfigPath() {
        return StringUtil.isNotEmpty(PARAM_MAP.get(CONFIG_PATH));
    }

    public static String getConfigPath() {
        return PARAM_MAP.get(CONFIG_PATH);
    }

    public static void main(String[] args) {

        try {
//            if (args.length > 0 ) {
//                for ( int i = 0; i < args.length; i = i + 2) {
//                    PARAM_MAP.put(args[i], args[i + 1]);
//                }
//            }
//
//            if (StringUtil.isEmpty(System.getProperty("candice.home"))) {
//                System.setProperty("candice.home", System.getProperty("user.home")
//                        + File.separator + "candice");
//            }
//            if (StringUtil.isEmpty(System.getProperty("candice.log.home"))) {
//                System.setProperty("candice.log.home", System.getProperty("candice.home"));
//            }
//
//            File file=new File(System.getProperty("candice.log.home"));
//            if(!file.exists() || !file.isDirectory()){
//                file.mkdirs();
//            }
//            file=new File(System.getProperty("candice.home"));
//            if(!file.exists() || !file.isDirectory()){
//                file.mkdirs();
//            }
//
//            System.out.println("candice.home-->"+System.getProperty("candice.home"));
//            System.out.println("candice.log.home-->"+System.getProperty("candice.log.home"));
//
//            String fp = null;
//
//            if (hasSelfConfigPath()) {
//                fp = new File(getConfigPath()).getPath() + File.separator + "candice.properties";
//            } else {
//                URL uri = CandiceStartup.class.getResource("/candice.properties");
//                fp = uri.getPath();
//            }
//
//            CandiceContext.load(fp);

            //init
            CandiceServer server = CandiceServer.getInstance();
            server.startup();

        } catch (Exception e) {
//            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//            LogLog.error(sdf.format(new Date()) + " startup error", e);
//            System.exit(-1);
            e.printStackTrace();
        } finally {
//            System.out.println("start candice time:" + (System.currentTimeMillis() - s) + "ms");
        }

    }
}
