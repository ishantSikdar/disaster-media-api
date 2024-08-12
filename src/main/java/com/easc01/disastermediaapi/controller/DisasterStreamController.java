package com.easc01.disastermediaapi.controller;

import com.easc01.disastermediaapi.constant.AppConstant;
import com.easc01.disastermediaapi.dto.ApiResponse;
import com.easc01.disastermediaapi.dto.youtube.YouTubeSearchListResponseDTO;
import com.easc01.disastermediaapi.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppConstant.API)
public class DisasterStreamController {

    private final YouTubeService youTubeService;

    @GetMapping(value = "/testYouTube")
    public ResponseEntity<ApiResponse<YouTubeSearchListResponseDTO>> getDisasters() {
        ApiResponse<YouTubeSearchListResponseDTO> apiResponse = new ApiResponse<>();
        apiResponse.setRequestId("!");

        try {
            YouTubeSearchListResponseDTO disasters = youTubeService.fetchRecentNaturalDisastersPosts();
            apiResponse.setData(disasters);
            apiResponse.setMessage("Disaster Details Fetched");
            apiResponse.setHttpStatus(HttpStatus.OK);

        } catch (Exception e) {
            log.error("Failed to get YouTube Disaster Data");
            apiResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            apiResponse.setMessage("Failed to get YouTube Disaster Data");
        }

        apiResponse.setTimestamp(Date.from(Instant.now()));
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
}
