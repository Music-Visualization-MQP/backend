package collageify.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Set the desired pool size
        executor.setMaxPoolSize(20); // Set the maximum pool size
        executor.setQueueCapacity(100); // Set the queue capacity
        executor.setThreadNamePrefix("MySpringApp-");
        executor.initialize();
        return executor;
    }

}
