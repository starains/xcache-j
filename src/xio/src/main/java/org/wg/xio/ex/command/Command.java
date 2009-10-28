package org.wg.xio.ex.command;

import org.wg.xio.context.Context;

/**
 * 命令
 * @author enychen Oct 11, 2009
 */
public interface Command {

    /**
     * 执行命令
     * @param commandMessage 命令消息
     * @param context 上下文
     */
    void execute(CommandMessage commandMessage, Context context);

}
