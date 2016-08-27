package com.coolv1994.jsonplayerlist;

import java.util.logging.Logger;

/**
 *
 * @author Vinnie
 */
public interface IPlugin {

    public JsonStats getStats();

    public String getConfigString(String path);

    public Logger getLogger();

}
