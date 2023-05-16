package com.myhome.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "BuildingSaleApiExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(15); // 기본적으로 실행을 대기하고 있는 Thread의 갯수
        executor.setMaxPoolSize(20); // 동시 동작하는, 최대 Thread 갯수
        executor.setQueueCapacity(3000); // MaxPoolSize를 초과하는 요청이 Thread 생성 요청시 해당 내용을 Queue에 저장하게 되고, 사용할수 있는 Thread 여유 자리가 발생하면 하나씩 꺼내져서 동작하게 된다.
        executor.setThreadNamePrefix("BuildingSaleApiExecutor");// spring이 생성하는 쓰레드의 접두사
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); //초과요청시 예외발생
        return executor;
    }
}
