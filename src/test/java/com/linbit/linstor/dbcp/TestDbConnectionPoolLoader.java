package com.linbit.linstor.dbcp;

import com.linbit.linstor.core.cfg.CtrlConfig;

import java.util.Properties;

public class TestDbConnectionPoolLoader
{
    private static final String DB_TYPE = "h2";
    private static final String DB_URL = "jdbc:h2:mem:testDB";
    private static final String DB_USER = "linstor";
    private static final String DB_PASSWORD = "linbit";
    private static final Properties DB_PROPS = new Properties();

    public DbConnectionPool loadDbConnectionPool()
    {
        // load the clientDriver...
        DB_PROPS.setProperty("create", "true");
        DB_PROPS.setProperty("user", DB_USER);
        DB_PROPS.setProperty("password", DB_PASSWORD);

        DbConnectionPool dbConnPool = new DbConnectionPool(new CtrlConfig(null));
        dbConnPool.initializeDataSource(DB_URL);

        return dbConnPool;
    }

    public String getDbType()
    {
        return DB_TYPE;
    }
}
