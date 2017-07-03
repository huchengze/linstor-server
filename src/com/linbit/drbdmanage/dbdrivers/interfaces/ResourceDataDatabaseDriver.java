package com.linbit.drbdmanage.dbdrivers.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.linbit.MapDatabaseDriver;
import com.linbit.TransactionMgr;
import com.linbit.drbdmanage.NodeData;
import com.linbit.drbdmanage.ResourceData;
import com.linbit.drbdmanage.Volume;
import com.linbit.drbdmanage.VolumeNumber;
import com.linbit.drbdmanage.propscon.SerialGenerator;
import com.linbit.drbdmanage.stateflags.StateFlagsPersistence;

public interface ResourceDataDatabaseDriver
{
    public MapDatabaseDriver<VolumeNumber, Volume> getVolumeMapDriver();

    public StateFlagsPersistence getStateFlagPersistence();

    public ResourceData load(Connection con, NodeData node, SerialGenerator serialGen, TransactionMgr transMgr)
        throws SQLException;
}
