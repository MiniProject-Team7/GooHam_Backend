package com.uplus.ureka.service.participation;

import com.uplus.ureka.exception.CustomExceptions;
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

    // 참여 신청
    public ParticipationResponseDTO applyParticipation(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();
        // 사용자 확인
        if(!participationMapper.checkUserExists(userId)){
            throw new CustomExceptions("존재하지 않는 회원입니다.");
        }
        // 게시물 확인
        if (!participationMapper.checkExistPost(postId)) {
            throw new CustomExceptions("해당 게시글이 존재하지 않습니다.");
        }
        // 중복 신청 확인
        if (participationMapper.checkExistParticipation(userId, postId)) {
            throw new CustomExceptions("이미 신청한 게시글입니다.");
        }
        // 모집 가능 확인
        if (!participationMapper.checkPostStatusValid(postId) || !participationMapper.checkPersonAvailable(postId)) {
            throw new CustomExceptions("현재 모집이 불가능한 상태입니다.");
        }

        // DTO를 전달하여 applyParticipation 호출
        participationMapper.applyParticipation(requestDTO);

        // 생성된 participantId를 확인
        ParticipationResponseDTO responseDTO = participationMapper.findUserParticipationStatus(userId, postId);

        return responseDTO;
    }

    public void cancelParticipation(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();
        // 신청 기록 확인
        if (!participationMapper.checkExistParticipation(userId, postId)) {
            throw new IllegalArgumentException("신청 기록이 없습니다.");
        }
        // 사용자 확인
        if(!participationMapper.checkUserExists(userId)){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        // 게시물 확인
        if (!participationMapper.checkExistPost(postId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
        participationMapper.cancelParticipation(requestDTO.getUserId(), requestDTO.getPostId());
    }

    public void approveParticipation(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();

        // 사용자 확인
        if(!participationMapper.checkUserExists(userId)){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        // 게시물 확인
        if (!participationMapper.checkExistPost(postId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }

        // 신청 기록 확인
        if (!participationMapper.checkExistParticipation(userId, postId)) {
            throw new IllegalArgumentException("신청한 기록이 없습니다.");
        }
        // 최대 정원 확인
        if (!participationMapper.checkPersonAvailable(postId)) {
            throw new IllegalStateException("최대 인원을 초과하여 승인할 수 없습니다.");
        }
        participationMapper.approveParticipation(userId, postId);
        //알림 추가 필요
        participationMapper.increaseCurrentPerson(postId);
    }

    public void rejectParticipation(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();
        // 사용자 확인
        if(!participationMapper.checkUserExists(userId)){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        // 게시물 확인
        if (!participationMapper.checkExistPost(postId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
        // 신청 기록 확인
        if (!participationMapper.checkExistParticipation(userId, postId)) {
            throw new IllegalArgumentException("신청한 기록이 없습니다.");
        }
        participationMapper.rejectParticipation(requestDTO.getUserId(), requestDTO.getPostId());
        participationMapper.deleteFromParticipationQueue(requestDTO.getUserId(), requestDTO.getPostId());
    }

    public List<ParticipationResponseDTO> findAllParticipantsByPostId(Long postId) {
        // 게시물 확인
        if (!participationMapper.checkExistPost(postId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }

        return participationMapper.findAllParticipantsByPostId(postId);
    }

    public ParticipationResponseDTO findUserParticipationStatus(ParticipationRequestDTO requestDTO) {
        Long userId = requestDTO.getUserId();
        Long postId = requestDTO.getPostId();
        // 사용자 확인
        if(!participationMapper.checkUserExists(userId)){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        // 게시물 확인
        if (!participationMapper.checkExistPost(postId)) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }
        return participationMapper.findUserParticipationStatus(requestDTO.getUserId(), requestDTO.getPostId());
    }
}
