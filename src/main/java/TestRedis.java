import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: sqs
 * @Date: 2019/11/20 16:31
 * @Description:
 * @@Version: 1.0
 */
public class TestRedis {

    @Test
    public void testJedis(){
        Jedis jedis = new Jedis("192.168.230.128",6379);

        System.out.println(jedis.get("name"));

        jedis.set("password","12323");

        jedis.close();
    }


    @Test
    public void testJedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        //最大闲置数
        jedisPoolConfig.setMaxIdle(30);
        //最小闲置数
        jedisPoolConfig.setMinIdle(10);
        //最大连接数
        jedisPoolConfig.setMaxTotal(50);

        JedisPool pool = new JedisPool(jedisPoolConfig,"192.168.230.128",6379);

        Jedis jedis = pool.getResource();

        jedis.set("name","32");
        System.out.println(jedis.get("name"));

        jedis.close();
        pool.close();
    }
}
