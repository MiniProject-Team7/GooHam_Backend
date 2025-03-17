package com.uplus.ureka.controller;

import com.uplus.ureka.dto.participation.ParticipationRequestDTO;
import com.uplus.ureka.dto.participation.ParticipationResponseDTO;
import com.uplus.ureka.service.participation.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gooham/participants")
@RequiredArgsConstructor
public class ParticipationController {
    private final ParticipationService participationService;

    // ✅ 참여 신청 (RequestBody 사용)
    @PostMapping
    public ResponseEntity<String> applyParticipation(@RequestBody ParticipationRequestDTO requestDTO) {
        participationService.applyParticipation(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("참여 신청이 완료되었습니다.");
    }

    // ✅ 참여 취소 (DELETE는 RequestBody 지원 X → PathVariable 사용)
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<String> cancelParticipation(@PathVariable Long userId, @PathVariable Long postId) {
        ParticipationRequestDTO requestDTO = new ParticipationRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setPostId(postId);

        participationService.cancelParticipation(requestDTO);
        return ResponseEntity.ok("참여 신청이 취소되었습니다.");
    }

    // ✅ 참여 승인 (RequestBody 사용)
    @PatchMapping("/approve")
    public ResponseEntity<String> approveParticipation(@RequestBody ParticipationRequestDTO requestDTO) {
        participationService.approveParticipation(requestDTO);
        return ResponseEntity.ok("참여 신청이 승인되었습니다.");
    }

    // ✅ 참여 거부 (RequestBody 사용)
    @PatchMapping("/reject")
    public ResponseEntity<String> rejectParticipation(@RequestBody ParticipationRequestDTO requestDTO) {
        participationService.rejectParticipation(requestDTO);
        return ResponseEntity.ok("참여 신청이 거부되었습니다.");
    }

    // ✅ 특정 게시글의 전체 참여 목록 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<ParticipationResponseDTO>> findAllParticipants(@PathVariable Long postId) {
        List<ParticipationResponseDTO> participants = participationService.findAllParticipantsByPostId(postId);
        return ResponseEntity.ok(participants);
    }

    // ✅ 특정 사용자의 참여 신청 상태 조회
    @GetMapping("/{userId}/{postId}/status")
    public ResponseEntity<ParticipationResponseDTO> findUserParticipationStatus(
            @PathVariable Long userId, @PathVariable Long postId) {
        ParticipationRequestDTO requestDTO = new ParticipationRequestDTO();
        requestDTO.setUserId(userId);
        requestDTO.setPostId(postId);

        ParticipationResponseDTO response = participationService.findUserParticipationStatus(requestDTO);
        return ResponseEntity.ok(response);
    }
}
