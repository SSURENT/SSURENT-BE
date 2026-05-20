package ssurent.ssurentbe.common.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.item.entity.ItemStatusLog;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.item.enums.Condition;
import ssurent.ssurentbe.domain.item.repository.ItemStatusLogRepository;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.rental.repository.RentalRepository;
import ssurent.ssurentbe.domain.users.entity.UserPenaltyLog;
import ssurent.ssurentbe.domain.users.entity.Users;
import ssurent.ssurentbe.domain.users.enums.PenaltyTypes;
import ssurent.ssurentbe.domain.users.enums.Status;
import ssurent.ssurentbe.domain.users.repository.UserPenaltyLogRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OverdueScheduler {

    private static final String CHANGED_BY_SYSTEM = "SYSTEM";
    private static final long BAN_THRESHOLD = 3L;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final RentalRepository rentalRepository;
    private final ItemStatusLogRepository itemStatusLogRepository;
    private final UserPenaltyLogRepository userPenaltyLogRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void processOverdue() {
        LocalDateTime now = LocalDateTime.now(KST);
        List<RentalHistory> candidates = rentalRepository.findOverdueCandidates(now);

        int bannedCount = 0;
        for (RentalHistory rh : candidates) {
            rh.markOverdue();

            Items item = rh.getItemId();
            Condition prevCondition = item.getCondition();
            item.updateCondition(Condition.OVERDUE);
            itemStatusLogRepository.save(ItemStatusLog.builder()
                    .itemName(item.getItemName() + "(" + item.getItemNum() + ")")
                    .changedByInfo(CHANGED_BY_SYSTEM)
                    .prevStatus(prevCondition)
                    .newStatus(Condition.OVERDUE)
                    .build());

            Users user = rh.getUserId();
            userPenaltyLogRepository.save(UserPenaltyLog.builder()
                    .userId(user)
                    .itemName(item.getItemName())
                    .penaltyType(PenaltyTypes.OVERDUE)
                    .build());

            long penaltyCount = userPenaltyLogRepository.countByUserId(user);
            if (penaltyCount >= BAN_THRESHOLD && user.getStatus() != Status.BANNED) {
                user.updateStatus(Status.BANNED);
                bannedCount++;
            }
        }

        log.info("Overdue processed - rentals: {}, banned users: {}", candidates.size(), bannedCount);
    }
}
