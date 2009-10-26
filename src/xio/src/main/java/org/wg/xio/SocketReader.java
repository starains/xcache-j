package org.wg.xio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wg.xio.context.Context;

/**
 * socket读取器
 * @author enychen Oct 14, 2009
 */
public class SocketReader implements Runnable {

    /** log */
    private static final Log log = LogFactory.getLog(SocketReader.class);

    /** 上下文 */
    private Context          context;

    /**  */
    private SocketChannel    socketChannel;

    /** 消息处理器 */
    private MessageHandler   messageHandler;

    /** 缓冲区大小 */
    private int              bufferSize;

    /**
     * 创建socket读取器
     * @param context 上下文
     */
    public SocketReader(Context context) {
        this.context = context;
        this.socketChannel = context.getSocketChannel();
        this.messageHandler = context.getServerSupporter().getMessageHandler();
        this.bufferSize = context.getServerSupporter().getServerConfig().getBufferSize();
    }

    /*
     * @see java.lang.Runnable#run()
     */
    public void run() {
        int readLength = 0;

        try {
            ByteBuffer message = ByteBuffer.allocate(this.bufferSize);

            // --读取消息并处理
            while ((readLength = this.socketChannel.read(message)) > 0) {
                message.flip();

                this.context.receive(message);

                this.messageHandler.handle(context);

                message.clear();
            }

            // --关闭连接
            if (readLength < 0) {
                this.context.close();
            }
        } catch (Exception e) {
            log.error("socket读取异常！", e);

            // --IO异常，关闭连接
            if (e instanceof IOException) {
                this.context.close();
            }
        } finally {
            // --继续选择读取
            if (this.context.getSocketChannel().isOpen()) {
                context.resumeSelectRead();
            }
        }
    }

}