package com.linbit.linstor.core.cfg;

import java.util.function.Function;

public class LinstorEnvParser
{
    public static final String LS_CONFIG_DIRECTORY = "LS_CONFIG_DIRECTORY";
    public static final String LS_LOG_DIRECTORY = "LS_LOG_DIRECTORY";
    public static final String LS_LOG_LEVEL = "LS_LOG_LEVEL";

    public static void applyTo(LinstorConfig cfg)
    {
        cfg.setConfigDir(getEnv(LS_CONFIG_DIRECTORY, Function.identity(), ""));
        cfg.setLogDirectory(getEnv(LS_LOG_DIRECTORY, Function.identity(), "."));
        cfg.setLogLevel(getEnv(LS_LOG_LEVEL, Function.identity()));
    }

    protected static String getEnv(String env)
    {
        return getEnv(env, Function.identity(), null);
    }

    protected static <T> T getEnv(String env, Function<String, T> func)
    {
        return getEnv(env, func, null);
    }

    protected static <T> T getEnv(String env, Function<String, T> func, T dfltValue)
    {
        String envVal = System.getenv(env);
        return envVal == null ? dfltValue : func.apply(envVal);
    }
}
