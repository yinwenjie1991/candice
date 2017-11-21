package io.candice.config.loader.xml;

import io.candice.CandiceContext;
import io.candice.config.model.ClusterConfig;
import io.candice.config.model.QuarantineConfig;
import io.candice.config.model.SystemConfig;
import io.candice.config.model.UserConfig;
import io.candice.config.util.ConfigException;
import io.candice.config.util.ConfigUtil;
import io.candice.config.util.ParameterMapping;
import io.candice.route.util.StringUtil;
import io.candice.server.security.KeyPairGen;
import io.candice.util.SplitUtil;
import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class XMLServerLoader {

    private final SystemConfig system;
    private final Map<String, UserConfig> users;
    private final QuarantineConfig quarantine;
    private ClusterConfig cluster;

    private static final Logger logger = Logger.getLogger(XMLServerLoader.class);

    public XMLServerLoader(String configFolder) {
        this.system = new SystemConfig();
        this.users = new HashMap<String, UserConfig>();
        this.quarantine = new QuarantineConfig();
        this.load(configFolder);
    }

    public SystemConfig getSystem() {
        return system;
    }

    public Map<String, UserConfig> getUsers() {
        return (Map<String, UserConfig>) (users.isEmpty() ? Collections.emptyMap() : Collections
                .unmodifiableMap(users));
    }

    public QuarantineConfig getQuarantine() {
        return quarantine;
    }

    public ClusterConfig getCluster() {
        return cluster;
    }

    private void load(String configFolder) {
        InputStream xml = null;
        try {
            if (StringUtil.isEmpty(configFolder)) {
                xml = XMLServerLoader.class.getResourceAsStream("/server.xml");
            } else {
                xml = new FileInputStream(configFolder + File.separator + "server.xml");
            }

            Element root = ConfigUtil.getDocument(xml).getDocumentElement();
            loadSystem(root);
            loadUsers(root);
            this.cluster = new ClusterConfig(root, system.getServerPort());
            loadQuarantine(root);
        } catch (ConfigException e) {
            throw e;
        } catch (Throwable e) {
            throw new ConfigException(e);
        } finally {

            if (xml != null) {
                try {
                    xml.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void loadQuarantine(Element root) {
        NodeList list = root.getElementsByTagName("host");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                Element e = (Element) node;
                String host = e.getAttribute("name").trim();
                if (quarantine.getHosts().containsKey(host)) {
                    throw new ConfigException("host duplicated : " + host);
                }

                Map<String, Object> props = ConfigUtil.loadElements(e);
                String[] users = SplitUtil.split((String) props.get("user"), ',', true);
                HashSet<String> set = new HashSet<String>();
                if (null != users) {
                    for (String user : users) {
                        UserConfig uc = this.users.get(user);
                        if (null == uc) {
                            throw new ConfigException("[user: " + user
                                    + "] doesn't exist in [host: " + host + "]");
                        }

                        if (null == uc.getSchemas() || uc.getSchemas().size() == 0) {
                            throw new ConfigException("[host: " + host
                                    + "] contains one root privileges user: "
                                    + user);
                        }
                        if (set.contains(user)) {
                            throw new ConfigException("[host: " + host
                                    + "] contains duplicate user: " + user);
                        } else {
                            set.add(user);
                        }
                    }
                }
                quarantine.getHosts().put(host, set);
            }
        }
    }

    private void loadUsers(Element root) {
        NodeList list = root.getElementsByTagName("user");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                Element e = (Element) node;
                String name = e.getAttribute("name");
                UserConfig user = new UserConfig();
                user.setName(name);
                Map<String, Object> props = ConfigUtil.loadElements(e);
                boolean needEncrypt = props.get("needEncrypt") == null ? false : BooleanUtils
                        .toBoolean(props.get("needEncrypt").toString());

                String pwd = (String) props.get("password");
                if (needEncrypt) {
                    try {
                        user.setPassword(KeyPairGen.decrypt(CandiceContext.getPubKey(), pwd));

                    } catch (Exception e1) {
                        logger.error("密钥解密失败", e1);
                    }
                } else {
                    user.setPassword(pwd);
                }
                //System.out.println("ds password-->" + user.getPassword());

                String schemas = (String) props.get("schemas");
                if (schemas != null) {
                    String[] strArray = SplitUtil.split(schemas, ',', true);
                    user.setSchemas(new HashSet<String>(Arrays.asList(strArray)));
                }
                if (users.containsKey(name)) {
                    throw new ConfigException("user " + name + " duplicated!");
                }
                users.put(name, user);
            }
        }
    }

    private void loadSystem(Element root) throws IllegalAccessException, InvocationTargetException {
        NodeList list = root.getElementsByTagName("system");
        for (int i = 0, n = list.getLength(); i < n; i++) {
            Node node = list.item(i);
            if (node instanceof Element) {
                Map<String, Object> props = ConfigUtil.loadElements((Element) node);
                ParameterMapping.mapping(system, props);
            }
        }
    }
}
