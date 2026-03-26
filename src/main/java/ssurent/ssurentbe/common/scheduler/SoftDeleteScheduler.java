package ssurent.ssurentbe.common.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.assists.repository.AssistsRepository;
import ssurent.ssurentbe.domain.item.repository.CategoryRepository;
import ssurent.ssurentbe.domain.item.repository.ItemRepository;
import ssurent.ssurentbe.domain.users.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SoftDeleteScheduler {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AssistsRepository assistsRepository;

    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void cleanupSoftDeletedEntities() {
        LocalDateTime threshold = LocalDateTime.now().minusMonths(1);

        int items = itemRepository.hardDeleteSoftDeletedBefore(threshold);
        int categories = categoryRepository.hardDeleteSoftDeletedBefore(threshold);
        int users = userRepository.hardDeleteSoftDeletedBefore(threshold);
        int assists = assistsRepository.hardDeleteSoftDeletedBefore(threshold);

        log.info("Soft delete cleanup - items: {}, categories: {}, users: {}, assists: {}",
                items, categories, users, assists);
    }
}
