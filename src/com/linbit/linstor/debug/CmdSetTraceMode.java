package com.linbit.linstor.debug;

import com.linbit.linstor.logging.ErrorReporter;
import com.linbit.linstor.security.AccessContext;
import com.linbit.linstor.security.Privilege;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

public class CmdSetTraceMode extends BaseDebugCmd
{
    private static final String PRM_MODE_NAME   = "MODE";
    private static final String PRM_ENABLED     = "ENABLED";
    private static final String PRM_DISABLED    = "DISABLED";

    private static final Map<String, String> PARAMETER_DESCRIPTIONS = new TreeMap<>();
    static
    {
        PARAMETER_DESCRIPTIONS.put(
            PRM_MODE_NAME,
            "Specifies the TRACE level logging mode to set\n" +
            "    ENABLED\n" +
            "        Enables logging at the TRACE level\n" +
            "    DISABLED\n" +
            "        Disables logging at the TRACE level"
        );
    }

    public CmdSetTraceMode()
    {
        super(
            new String[]
            {
                "SetTrcMode"
            },
            "Set TRACE level logging mode",
            "Sets the TRACE level logging mode",
            PARAMETER_DESCRIPTIONS,
            null,
            false
        );
    }

    @Override
    public void execute(
        PrintStream debugOut,
        PrintStream debugErr,
        AccessContext accCtx,
        Map<String, String> parameters
    )
        throws Exception
    {
        String prmMode = parameters.get(PRM_MODE_NAME);
        if (prmMode != null)
        {
            AccessContext privCtx = accCtx.clone();
            privCtx.getEffectivePrivs().enablePrivileges(Privilege.PRIV_SYS_ALL);
            ErrorReporter errLog = coreSvcs.getErrorReporter();
            if (prmMode.equalsIgnoreCase(PRM_ENABLED))
            {
                errLog.setTraceEnabled(privCtx, true);
                debugOut.println("New TRACE level logging mode: ENABLED");
            }
            else
            if (prmMode.equalsIgnoreCase(PRM_DISABLED))
            {
                errLog.setTraceEnabled(privCtx, false);
                debugOut.println("New TRACE level logging mode: DISABLED");
            }
            else
            {
                printError(
                    debugErr,
                    "The specified TRACE level logging mode can not be set",
                    "The value specified for the " + PRM_MODE_NAME + " parameter is invalid",
                    "Specify a valid value for the " + PRM_MODE_NAME + " parameter.\n" +
                    "Valid values are:\n" +
                    "    " + PRM_ENABLED + "\n" +
                    "    " + PRM_DISABLED,
                    "The specified value was '" + prmMode + "'"
                );
            }
        }
        else
        {
            printMissingParamError(debugErr, PRM_MODE_NAME);
        }
    }
}
