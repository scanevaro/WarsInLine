package com.deeep.game.net;

/**
 * Created by E on 12/15/2014.
 */
public interface NetworkLayer {
    public void sentPacket(Packet packet);

    public Packet receivePacket();
}
