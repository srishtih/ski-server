import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Consumer class is responsible for reading messages from the RabbitMQ's queue and writing
 * this data into a redis database
 */
public class Consumer {
    protected static final String QUEUE_NAME = "rides";
    protected static final int THREAD_COUNT = 100;
    public static final JedisPool jPool = new JedisPool("redis-leader.default.svc.cluster.local", 6379);


    /**
     * Driver code for establishing connection to queue and creating channels to read queue
     * @param args Arguments, if any
     * @throws IOException for signalling connection related I/O exception
     * @throws TimeoutException for connection timeout related scenarios
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("hello-world.default.svc.cluster.local");
        factory.setUsername(System.getenv("RMQ_USERNAME"));
        factory.setPassword(System.getenv("RMQ_PASSWD"));

        Connection conn = factory.newConnection();

        //Use thread pool for churning out consumers
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.execute (new ConsumerThread(QUEUE_NAME, conn));
        }
    }
}
