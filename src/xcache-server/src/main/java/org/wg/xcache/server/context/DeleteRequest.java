package org.wg.xcache.server.context;

/**
 * 删除缓存请求
 * @author enychen Sep 6, 2009
 */
public class DeleteRequest extends Request {

    /** 缓存键 */
    protected String key;

    /*
     * (non-Javadoc)
     * @see org.wg.xcache.server.context.Request#decode(byte[])
     */
    @Override
    public void decode(byte[] bytes) {
        // TODO Auto-generated method stub

    }

}