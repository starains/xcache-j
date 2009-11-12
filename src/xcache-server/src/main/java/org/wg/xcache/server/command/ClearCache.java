package org.wg.xcache.server.command;

import org.wg.xcache.Cache;
import org.wg.xcache.protocol.ClearCacheRequest;
import org.wg.xio.context.Context;
import org.wg.xio.ex.command.CommandRequest;

/**
 * 清除缓存
 * @author enychen Nov 12, 2009
 */
public class ClearCache extends XcacheCommand {

    /* (non-Javadoc)
     * @see org.wg.xcache.server.command.XcacheCommand#execute(java.lang.Object, org.wg.xio.ex.command.CommandRequest, org.wg.xio.context.Context)
     */
    @Override
    protected void execute(Object request, CommandRequest commandRequest, Context context) {
        ClearCacheRequest clearCacheRequest = (ClearCacheRequest) request;
        Cache cache = this.xcacheManager.getCache(clearCacheRequest.getCacheName());
        
        cache.clear();
    }

}
