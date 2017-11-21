package io.candice.config.model;

import java.util.Set;

/**
 * 文件描述:
 * 作者: yinwenjie
 * 日期: 2017-09-19
 */
public class UserConfig {
    private String      name;
    private boolean     needEncrypt = false;

    private String      password;
    private Set<String> schemas;

    /**
     * Getter method for property <tt>needEncrypt</tt>.
     *
     * @return property value of needEncrypt
     */
    public boolean isNeedEncrypt() {
        return needEncrypt;
    }

    /**
     * Setter method for property <tt>needEncrypt</tt>.
     *
     * @param needEncrypt value to be assigned to property needEncrypt
     */
    public void setNeedEncrypt(boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(Set<String> schemas) {
        this.schemas = schemas;
    }
}
