package com.linbit.linstor.dbdrivers;

import com.linbit.linstor.LinStorDBRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SQLUtils
{
    private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static List<String> getAsStringList(ResultSet resultSet, String columnName)
    {
        List<String> list;
        try
        {
            list = OBJ_MAPPER.readValue(
                resultSet.getString(columnName),
                List.class
            );
        }
        catch (IOException | SQLException exc)
        {
            throw new LinStorDBRuntimeException(
                "Exception occurred while deserializing from json array",
                exc
            );
        }
        return list;
    }

    public static void setJsonIfNotNull(PreparedStatement stmt, int idx, Object obj) throws SQLException
    {
        if (obj != null)
        {
            try
            {
                stmt.setString(idx, OBJ_MAPPER.writeValueAsString(obj));
            }
            catch (IOException exc)
            {
                throw new LinStorDBRuntimeException(
                    "Exception occurred while serializing to json array: " + obj.toString(),
                    exc
                );
            }
        }
        else
        {
            stmt.setNull(idx, Types.VARCHAR);
        }
    }

    public static void setIntIfNotNull(PreparedStatement stmt, int idx, Integer val) throws SQLException
    {
        if (val != null)
        {
            stmt.setInt(idx, val);
        }
        else
        {
            stmt.setNull(idx, Types.INTEGER);
        }
    }

    public static void setStringIfNotNull(PreparedStatement stmt, int idx, String str) throws SQLException
    {
        if (str != null)
        {
            stmt.setString(idx, str);
        }
        else
        {
            stmt.setNull(idx, Types.VARCHAR);
        }
    }

    public static void runSql(Connection con, BufferedReader br)
        throws IOException, SQLException
    {
        StringBuilder cmdBuilder = new StringBuilder();
        for (String line = br.readLine(); line != null; line = br.readLine())
        {
            String trimmedLine = line.trim();
            if (!trimmedLine.startsWith("--"))
            {
                cmdBuilder.append("\n").append(trimmedLine);
                if (trimmedLine.endsWith(";"))
                {
                    cmdBuilder.setLength(cmdBuilder.length() - 1); // cut the ;
                    String cmd = cmdBuilder.toString();
                    cmdBuilder.setLength(0);
                    GenericDbUtils.executeStatement(con, cmd);
                }
            }
        }
        String nonTerminatedStatement = cmdBuilder.toString();
        if (!nonTerminatedStatement.trim().isEmpty())
        {
            GenericDbUtils.executeStatement(con, nonTerminatedStatement);
        }
    }

    public static void runSql(final Connection con, final String script)
        throws SQLException
    {
        StringBuilder cmdBuilder = new StringBuilder();
        Scanner scanner = new Scanner(script);
        while (scanner.hasNextLine())
        {
            String trimmedLine = scanner.nextLine().trim();
            if (!trimmedLine.startsWith("--"))
            {
                cmdBuilder.append("\n").append(trimmedLine);
                if (trimmedLine.endsWith(";"))
                {
                    cmdBuilder.setLength(cmdBuilder.length() - 1); // cut the ;
                    String cmd = cmdBuilder.toString();
                    cmdBuilder.setLength(0);
                    GenericDbUtils.executeStatement(con, cmd);
                }
            }
        }
        scanner.close();
        String nonTerminatedStatement = cmdBuilder.toString();
        if (!nonTerminatedStatement.trim().isEmpty())
        {
            GenericDbUtils.executeStatement(con, nonTerminatedStatement);
        }
    }


    public static <T> List<T> getAsTypedList(
        ResultSet resultSet,
        String columnName,
        Function<String, T> convertFunction
    )
    {
        List<T> ret = new ArrayList<>();
        List<String> listStr = getAsStringList(resultSet, columnName);

        for (String strElement : listStr)
        {
            ret.add(convertFunction.apply(strElement));
        }

        return ret;
    }

    public static Integer getNullableInteger(ResultSet resultSetRef, String column) throws SQLException
    {
        Integer ret = resultSetRef.getInt(column);
        if (resultSetRef.wasNull())
        {
            ret = null;
        }
        return ret;
    }

    public static Boolean getNullableBoolean(ResultSet resultSetRef, String column) throws SQLException
    {
        Boolean ret = resultSetRef.getBoolean(column);
        if (resultSetRef.wasNull())
        {
            ret = null;
        }
        return ret;
    }

}
