package org.yuanfang.rabbit.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.yuanfang.BaseTest;
import org.yuanfang.rabbit.common.constant.RabbitMQConstant;
import org.yuanfang.rabbit.common.constant.SpringConstant;
import org.yuanfang.rabbit.vo.ExampleEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: chenfangzhi
 * @Description:
 * @Date: 2018/9/25-1:12
 * @ModifiedBy:
 */
@ActiveProfiles(SpringConstant.DIRECT_LISTENER_PROFILE)
@Slf4j
public class DirectConsumerTest extends BaseTest {

    @Test
    @SneakyThrows
    public void process() {
        //为了防止交换器还没有创建
        Thread.sleep(1 * 1000);
        Executors.newSingleThreadScheduledExecutor()
            .scheduleAtFixedRate(
                this::send, 0, 3, TimeUnit.SECONDS);
        Thread.sleep(12 * 1000);
    }

    private void send() {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_KEY,
                new ExampleEvent(id.getAndIncrement(), "message from direct!"));
        } catch (Exception e) {
            log.error("send error !", e);
        }
    }
}