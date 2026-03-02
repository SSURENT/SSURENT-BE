package ssurent.ssurentbe.domain.assists.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;
import ssurent.ssurentbe.domain.assists.dto.request.AdminAssistCreateRequest;
import ssurent.ssurentbe.domain.assists.dto.response.AdminAssistResponse;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.assists.repository.AssistsRepository;

@Service
@RequiredArgsConstructor
public class AssistCommandService {
    private final AssistsRepository assistsRepository;

    @Transactional
    public AdminAssistResponse createAssists(AdminAssistCreateRequest request) {
        Assists assist = Assists.builder()
                .name(request.assistName())
                .build();
        return AdminAssistResponse.from(assistsRepository.save(assist));
    }

    @Transactional
    public void deleteAssist(Long assistId) {
        Assists assist = assistsRepository.findById(assistId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.ASSIST_NOT_FOUND));

        assist.softDelete();
    }
}
