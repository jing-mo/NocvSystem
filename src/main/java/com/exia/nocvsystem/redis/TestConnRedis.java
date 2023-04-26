package com.exia.nocvsystem.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @author exia
 * @version 1.0
 * Create by 2023/4/23 20:38
 */

public class TestConnRedis {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        // 1.String类型【确诊：222，死亡：00】
        jedis.set("nocv","Coding路人王");
        System.out.println(jedis.get("nocv"));

        // 2.List集合【新闻列表】
        // 存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }

        // 3.Set无序集合,去重
        jedis.sadd("nocvset","111");
        jedis.sadd("nocvset","111");
        jedis.sadd("nocvset","111");
        jedis.sadd("nocvset","111");
        jedis.sadd("nocvset","222");
        Set<String> nocvlist = jedis.smembers("nocvlist");
        for (String s : nocvlist){
            System.out.println(s);
        }

        // 4.Sorted Set 有序集合【排名，排序，获取排序码】
        jedis.zadd("nocvset2",86.9,"1111");
        jedis.zadd("nocvset2",56.8,"2222");
        jedis.zadd("nocvset2",86.5,"3333");
        jedis.zadd("nocvset2",88.9,"4444");
        jedis.zadd("nocvset2",100,"5555");
        Set<String> nocvset2 = jedis.zrange("nocvset2", 0, -1);
        for (String s : nocvset2){
            System.out.println(s);
        }

        Long nocvset21 = jedis.zrank("nocvset2", "4444");
        System.out.println(nocvset21);

        System.out.println("=========================");
        // 返回分数区间内的个数
        Long nocvset22 = jedis.zremrangeByScore("nocvset2", 88, 100);
        System.out.println(nocvset22);

        // 返回有序集中，成员的分数值
        Double nocvset23 = jedis.zscore("nocvset2", "5555");
        System.out.println(nocvset23); // 88.9

    }
}
