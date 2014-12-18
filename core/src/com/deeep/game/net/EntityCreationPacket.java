package com.deeep.game.net;

import com.deeep.game.entities.Entity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 8/13/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityCreationPacket extends Packet {
    /* The entity id */
    int entityId = 0;
    /* The entity type, to inform what entity it is */
    int entityType = 0;
    /* The position where the entity has been made */
    float x = 0, y = 0;
    /* The id which owns the entity */
    int playerId = 0;


    public EntityCreationPacket() {
        super();
    }

    public EntityCreationPacket(int connectionId, int entityId, int entityType, int x, int y) {
        super(PacketManager.getPacketId(new EntityCreationPacket()), connectionId);
        this.entityId = entityId;
        this.entityType = entityType;
        this.x = x;
        this.y = y;
    }

    public EntityCreationPacket(int connectionId, Entity networkEntity) {
        super(PacketManager.getPacketId(new EntityCreationPacket()), connectionId);
        this.entityId = networkEntity.getEntityId();
        this.entityType = networkEntity.getEntityType();
        this.x = networkEntity.getX();
        this.y = networkEntity.getY();
        this.playerId = networkEntity.getPlayerId();
    }

    @Override
    public void readPacketSpecific(DataInputStream in) throws IOException {
        playerId = in.readInt();
        entityType = in.readInt();
        entityId = in.readInt();
        x = in.readInt();
        y = in.readInt();
    }

    @Override
    public void writePacketSpecific(DataOutputStream out) throws IOException {
        out.writeInt(playerId);
        out.writeInt(entityType);
        out.writeInt(entityId);
        out.writeFloat(x);
        out.writeFloat(y);
    }

    public byte[] getBytes(){
        return null;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
