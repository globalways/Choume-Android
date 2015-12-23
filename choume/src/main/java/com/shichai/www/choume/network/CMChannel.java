package com.shichai.www.choume.network;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by wyp on 15/12/22.
 */
public class CMChannel {
    public static ManagedChannel buildCM(){
        ManagedChannel mChannel = ManagedChannelBuilder.forAddress(HttpConfig.CMRPCHost, HttpConfig.CMRPCPort)
                .usePlaintext(true)
                .build();
        return  mChannel;
    }
    public static ManagedChannel buildHT(){
        ManagedChannel mChannel = ManagedChannelBuilder.forAddress(HttpConfig.HTRPCHost, HttpConfig.HTRPCPort)
                .usePlaintext(true)
                .build();
        return  mChannel;
    }
}
