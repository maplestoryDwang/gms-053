package net.sf.odinms.tools;


/**
 * TODO
 *
 * @author dwang
 * @version 1.0
 * @since 2026/7/5 20:41
 */
public class ReactorPacketCreator {

//    public static MaplePacket destroyReactor(Reactor reactor) {
//        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
//        mplew.writeShort(SendPacketOpcode.REACTOR_DESTROY.getValue());
//
//        mplew.writeInt(reactor.getObjectId());
//        mplew.writeByte(reactor.getState());
//        mplew.writePos(reactor.getPosition());
//
//
//        return mplew.getPacket();
//    }
//
//
//    public static MaplePacket spawnReactor(Reactor reactor) {
//        MaplePacketLittleEndianWriter p = new MaplePacketLittleEndianWriter();
//        p.writeShort(SendPacketOpcode.REACTOR_SPAWN.getValue());
//        p.writeInt(reactor.getObjectId());
//        p.writeInt(reactor.getId());
//        p.writeByte(reactor.getState());
//        p.writePos(reactor.getPosition());
//        p.writeByte(0);
//        p.writeShort(0);
//        return p.getPacket();
//    }
//
//    public static MaplePacket triggerReactor(Reactor reactor, int stance) {
//        MaplePacketLittleEndianWriter p = new MaplePacketLittleEndianWriter();
//        p.writeShort(SendPacketOpcode.REACTOR_HIT.getValue());
//        p.writeInt(reactor.getObjectId());
//        p.writeByte(reactor.getState());
//        p.writePos(reactor.getPosition());
//        p.writeByte(stance);
//        p.writeShort(0);
//        p.writeByte(5); // frame delay, set to 5 since there doesn't appear to be a fixed formula for it
//        return p.getPacket();
//    }
}
