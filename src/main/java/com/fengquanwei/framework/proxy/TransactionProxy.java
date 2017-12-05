package com.fengquanwei.framework.proxy;

import com.fengquanwei.framework.annotation.Transaction;
import com.fengquanwei.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事务代理
 *
 * @author fengquanwei
 * @create 2017/12/5 15:56
 **/
public class TransactionProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();

        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);

            try {
                DatabaseHelper.beginTransaction();
                logger.info("开启事务");

                result = proxyChain.doProxyChain();

                DatabaseHelper.commitTransaction();
                logger.info("提交事务");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                logger.info("回滚事务");
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }
}
