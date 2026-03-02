package ssurent.ssurentbe.domain.assists.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssurent.ssurentbe.domain.assists.dto.response.AdminAssistResponse;
import ssurent.ssurentbe.domain.assists.entity.Assists;
import ssurent.ssurentbe.domain.assists.repository.AssistsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistQueryService {
    private final AssistsRepository assistsRepository;

    @Transactional
    public List<AdminAssistResponse> getAssists() {
        List<Assists> assistList = assistsRepository.findAll();
        return assistList.stream()
                .map(AdminAssistResponse::from)
                .toList();
    }
}
