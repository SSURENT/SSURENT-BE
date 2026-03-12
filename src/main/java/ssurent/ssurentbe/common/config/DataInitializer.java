package ssurent.ssurentbe.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.assists.repository.AssistsRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private static final String SUPERADMIN_ASSIST_NAME = "최고관리자";

    private final AssistsRepository assistsRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (assistsRepository.findByNameAndDeletedFalse(SUPERADMIN_ASSIST_NAME).isEmpty()) {
            assistsRepository.save(
                    Assists.builder()
                            .name(SUPERADMIN_ASSIST_NAME)
                            .deleted(false)
                            .build()
            );
        }
    }
}