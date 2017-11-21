package io.candice;

import io.candice.config.CandiceConfig;
import io.candice.config.model.UserConfig;
import io.candice.net.handler.FrontendPrivileges;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-10-14
 */
public class CandicePrivileges implements FrontendPrivileges{
    private static final Logger ALARM = Logger.getLogger("alarm");

    public boolean schemaExists(String schema) {
        CandiceConfig config = CandiceServer.getInstance().getConfig();
        return config.getSchemas().containsKey(schema);
    }

    public boolean userExists(String user, String host) {
        CandiceConfig config = CandiceServer.getInstance().getConfig();
        Map<String, Set<String>> quarantineHosts = config.getQuarantine().getHosts();
        if (quarantineHosts.containsKey(host)) {
            boolean rs = quarantineHosts.get(host).contains(user);
            if (!rs) {
                ALARM.error(new StringBuilder().append("#!QT_ATTACK#").append("[host=").append(host)
                        .append(",user=").append(user).append(']').toString());
            }
            return rs;
        } else {
            return config.getUsers().containsKey(user);
        }
    }

    public String getPassword(String user) {
        CandiceConfig conf = CandiceServer.getInstance().getConfig();
//        if (user != null && user.equals(conf.getSystem().getClusterHeartbeatUser())) {
//            return conf.getSystem().getClusterHeartbeatPass();
//        } else {
//            UserConfig uc = conf.getUsers().get(user);
//            if (uc != null) {
//                return uc.getPassword();
//            } else {
//                return null;
//            }
//        }
        UserConfig uc = conf.getUsers().get(user);
        if (uc != null) {
            return uc.getPassword();
        } else {
            return null;
        }
    }

    public Set<String> getUserSchemas(String user) {
        CandiceConfig conf = CandiceServer.getInstance().getConfig();
        UserConfig uc = conf.getUsers().get(user);
        if (uc != null) {
            return uc.getSchemas();
        } else {
            return null;
        }
    }
}
