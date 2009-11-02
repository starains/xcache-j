package org.wg.xcache.protocol.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.wg.xio.ex.command.CommandRequest;
import org.wg.xio.util.XioConst;

/**
 * �Ѷ�����뻺������
 * @author enychen Nov 2, 2009
 */
public class PutObjectRequest extends CommandRequest {

    /** ������ */
    protected String cacheName;

    /** ����� */
    protected String key;

    /** ������� */
    protected Object object;

    /** ����ʱ�䣬��λ���� */
    protected int    liveTime;

    /** ����ʱ�䣬��λ���� */
    protected int    idleTime;

    /**
     * �����Ѷ�����뻺������
     */
    public PutObjectRequest() {
    }

    /**
     * �����Ѷ�����뻺������
     * @param commandRequest ��������
     */
    public PutObjectRequest(CommandRequest commandRequest) {
        this.copy(commandRequest);
        this.decode(commandRequest.getMessage());
    }

    /*
     * (non-Javadoc)
     * @see org.wg.xio.ex.command.CommandRequest#encode()
     */
    @Override
    public ByteBuffer encode() {
        byte[] cacheNameBytes = this.cacheName.getBytes();
        int cacheNameLength = cacheNameBytes.length;

        byte[] keyBytes = this.key.getBytes();
        int keyLength = keyBytes.length;

        byte[] objectBytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream s = new ObjectOutputStream(baos);
            s.writeObject(this.object);
            s.flush();
            s.close();
            baos.flush();
            objectBytes = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int objectLength = objectBytes.length;

        this.length = XioConst.COMMAND_REQUEST_HEADER_LENGTH + XioConst.LENGTH_LENGTH
                + cacheNameLength + XioConst.LENGTH_LENGTH + cacheNameLength
                + XioConst.LENGTH_LENGTH + objectLength + XioConst.LENGTH_LENGTH
                + XioConst.LENGTH_LENGTH;

        ByteBuffer message = ByteBuffer.allocate(this.length);
        message.putInt(this.length);
        message.putInt(this.id);
        message.putInt(this.commandId);
        message.putInt(cacheNameLength);
        message.put(cacheNameBytes);
        message.putInt(keyLength);
        message.put(keyBytes);
        message.putInt(objectLength);
        message.putInt(this.liveTime);
        message.putInt(this.idleTime);
        message.flip();

        return message;
    }

    /*
     * (non-Javadoc)
     * @see org.wg.xio.ex.command.CommandRequest#decode(java.nio.ByteBuffer)
     */
    @Override
    public void decode(ByteBuffer message) {
        int cacheNameLength = message.getInt();
        byte[] cacheNameBytes = new byte[cacheNameLength];
        message.get(cacheNameBytes);
        this.cacheName = new String(cacheNameBytes);

        int keyLength = message.getInt();
        byte[] keyBytes = new byte[keyLength];
        message.get(keyBytes);
        this.key = new String(keyBytes);

        int objectLength = message.getInt();
        byte[] objectBytes = new byte[objectLength];
        message.get(objectBytes);

        ByteArrayInputStream bytesIn = null;
        try {
            bytesIn = new ByteArrayInputStream(objectBytes);
            ObjectInputStream objIs = new ObjectInputStream(bytesIn);
            this.object = objIs.readObject();
            objIs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.liveTime = message.getInt();
        this.idleTime = message.getInt();
    }

    /**
     * ��ȡ������
     * @return ������
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * ���û�����
     * @param cacheName ������
     */
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     * ��ȡ�����
     * @return �����
     */
    public String getKey() {
        return key;
    }

    /**
     * ���û����
     * @param key �����
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * ��ȡ�������
     * @return �������
     */
    public Object getObject() {
        return object;
    }

    /**
     * ���û������
     * @param object �������
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * ��ȡ����ʱ�䣬��λ����
     * @return ����ʱ�䣬��λ����
     */
    public int getLiveTime() {
        return liveTime;
    }

    /**
     * ��������ʱ�䣬��λ����
     * @param liveTime ����ʱ�䣬��λ����
     */
    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    /**
     * ����ʱ�䣬��λ����
     * @return ����ʱ�䣬��λ����
     */
    public int getIdleTime() {
        return idleTime;
    }

    /**
     * ���ÿ���ʱ�䣬��λ����
     * @param idleTime ����ʱ�䣬��λ����
     */
    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

}
