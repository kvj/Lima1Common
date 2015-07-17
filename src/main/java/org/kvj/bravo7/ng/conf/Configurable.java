package org.kvj.bravo7.ng.conf;

/**
 * Created by vorobyev on 7/17/15.
 */
public interface Configurable {

    public String getString(int name, String def);
    public void setString(int name, String value);

    public boolean getBool(int name, boolean def);
    public void setBool(int name, boolean value);

    public int getInt(int name, int def);
    public void setInt(int name, int value);

}
