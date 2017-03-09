package com.semion.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Created by heshuanxu on 2016/10/26.
 */
public class JedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    private static JedisPool jedisPool;

    private static String host;
    private static int port;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数默认8
        poolConfig.setMaxTotal(30);
        // 获取连接的最大等待毫秒数 默认-1
        poolConfig.setMaxWaitMillis(5000);
        // 最大空闲连接数, 默认8个
        poolConfig.setMaxIdle(20);//20
        //最小空闲连接数 默认0
        poolConfig.setMinIdle(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60000l);
        poolConfig.setTimeBetweenEvictionRunsMillis(3000l);
        poolConfig.setNumTestsPerEvictionRun(-1);

        jedisPool = new JedisPool(poolConfig, "127.0.0.1", 6379);
        logger.info("redis 连接INFO ：{}", ping());
        System.out.println("redis 连接结果：" + ping());
    }

    /**
     * 存储redis队列 --顺序存储
     *
     * @param key
     * @param value
     */
    public static void lpush(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            //jedisPool.returnResource(jedis); 在close 方法中释放资源到连接池
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    /**
     * 存储redis队列 --顺序存储队列头部
     *
     * @param key
     * @param value
     */
    public static void lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            //jedisPool.returnResource(jedis); 在close 方法中释放资源到连接池
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    /**
     * 存储redis队列 --反向存储
     *
     * @param key
     * @param value
     */
    public static void rpush(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            //jedisPool.returnResource(jedis); 在close 方法中释放资源到连接池
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    /**
     * 存储redis队列 --反向存储
     *
     * @param key
     * @param value
     */
    public static void rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            //jedisPool.returnResource(jedis); 在close 方法中释放资源到连接池
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取队列全部数据
     *
     * @param key
     * @return
     */
    public static List<byte[]> lrange(byte[] key) {
        Jedis jedis = null;
        List<byte[]> list = null;
        try {
            jedis = jedisPool.getResource();
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return list;
    }

    /**
     * 获取队列全部数据
     *
     * @param key
     * @return
     */
    public static List<String> lrange(String key) {
        Jedis jedis = null;
        List<String> list = null;
        try {
            jedis = jedisPool.getResource();
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return list;
    }


    /**
     * 获取并取出列表中的第一个元素并从队列删除
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.lpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取并取出列表中的第一个元素并从队列删除
     *
     * @param key
     * @return
     */
    public static byte[] lpop(byte[] key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.lpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return
     */
    public static String rpop(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取并取出列表中的最后一个元素
     *
     * @param key
     * @return
     */
    public static byte[] rpop(byte[] key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static byte[] get(byte[] key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取redis数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 测试是否连接成功
     *
     * @return
     */
    public static String ping() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ping();
        } catch (Exception e) {
            logger.error("连接redis 发生异常：{}", e);
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 通过key删除
     *
     * @param key
     */
    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    public static String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.hget(key, field);
            return value;
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

}
