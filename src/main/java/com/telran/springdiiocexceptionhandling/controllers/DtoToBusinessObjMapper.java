package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

import java.util.Map;

public interface DtoToBusinessObjMapper {
    Map<String, String> topicDtoToBo(TopicDto topicDto);

    TopicResponseDto topicBoToDto(Map<String, Object> topicBo);

    TopicFullDto topicBoToFullDto(Map<String, Object> topicFullBo);

    Map<String, String> commentAddDtotoBo(AddCommentDto addCommentDto);

    CommentFullDto commentBoToFullDto(Map<String, Object> commentFullDto);

    Map<String, String> commentUpdDtoToBo(UpdateCommentDto updateCommentDto);

}
