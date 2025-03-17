package com.uplus.ureka.service.participation;

import com.uplus.ureka.dto.participation.ParticipationRequestDTO;
import com.uplus.ureka.dto.participation.ParticipationResponseDTO;
import com.uplus.ureka.repository.participation.ParticipationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationService {
    private final ParticipationMapper participationMapper;

    // ✅ 참여 신청
    public void applyParticipation(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();

        if (!participationMapper.checkExistPost(postId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }

        if (participationMapper.checkExistParticipation(userId, postId)) {
            throw new IllegalStateException("이미 신청한 게시글입니다.");
        }

        if (!participationMapper.checkPostStatusValid(postId) || !participationMapper.checkPersonAvailable(postId)) {
            throw new IllegalStateException("현재 모집이 불가능한 상태입니다.");
        }

        // DTO를 전달하여 applyParticipation 호출
        participationMapper.applyParticipation(requestDTO);

        // 생성된 participantId를 확인
        System.out.println("Generated Participant ID: " + requestDTO.getParticipantId());
    }

    public void cancelParticipation(ParticipationRequestDTO requestDTO) {
        if (!participationMapper.checkExistParticipation(requestDTO.getUserId(), requestDTO.getPostId())) {
            throw new IllegalArgumentException("신청 기록이 없습니다.");
        }
        participationMapper.cancelParticipation(requestDTO.getUserId(), requestDTO.getPostId());
    }

    public void approveParticipation(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();

        if (!participationMapper.checkExistParticipation(userId, postId)) {
            throw new IllegalArgumentException("신청한 기록이 없습니다.");
        }

        if (!participationMapper.checkPersonAvailable(postId)) {
            throw new IllegalStateException("최대 인원을 초과하여 승인할 수 없습니다.");
        }

        participationMapper.approveParticipation(userId, postId);
        participationMapper.increaseCurrentPerson(postId);
        participationMapper.deleteFromParticipationQueue(userId, postId);
    }

    public void rejectParticipation(ParticipationRequestDTO requestDTO) {
        participationMapper.rejectParticipation(requestDTO.getUserId(), requestDTO.getPostId());
        participationMapper.deleteFromParticipationQueue(requestDTO.getUserId(), requestDTO.getPostId());
    }

    public List<ParticipationResponseDTO> findAllParticipantsByPostId(Long postId) {
        return participationMapper.findAllParticipantsByPostId(postId);
    }

    public ParticipationResponseDTO findUserParticipationStatus(ParticipationRequestDTO requestDTO) {
        return participationMapper.findUserParticipationStatus(requestDTO.getUserId(), requestDTO.getPostId());
    }
}
