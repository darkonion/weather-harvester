package pl.homeweather.weatherharvester.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.homeweather.weatherharvester.entity.Lightning;
import pl.homeweather.weatherharvester.utils.LimitedSizeQueue;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Scope(scopeName = "singleton")
public class RecentLightningService {

    private final Deque<Lightning> recentLightnings;

    public RecentLightningService(@Value("${harvester.lightning_queue.size}") Integer lightningQueueSize) {
        this.recentLightnings = new LimitedSizeQueue<>(lightningQueueSize);
    }

    public Mono<Boolean> addToQueue(Mono<Lightning> lightning) {
        return lightning
                .filter(Lightning::isNotEmpty)
                .doOnNext(l ->
                    log.info("New lightning registered: " + l))
                .map(recentLightnings::add);
    }

    public Mono<List<Lightning>> getLightnings() {
        return Mono.just(
                recentLightnings
                        .stream()
                        .filter(l -> l.getDate().isAfter(LocalDateTime.now().minusDays(1)))
                        .collect(Collectors.toList())
        );
    }

    public Mono<Void> cleanQueue() {
        recentLightnings.clear();
        return Mono.empty().then();
    }
}
