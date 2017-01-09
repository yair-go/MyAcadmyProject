package com.example.mailo.myacadmyproject.model.backend;

import com.example.mailo.myacadmyproject.model.datasource.List_DBManager;
import com.example.mailo.myacadmyproject.model.datasource.MySQL_DBManager;

/**
 * Created by mailo on 28/11/2016.
 */

public class DBManagerFactory {

    static DB_manager manager = null;

    public static DB_manager getManager() {
        if (manager == null)
            manager = new MySQL_DBManager();
        return manager;
    }
}
