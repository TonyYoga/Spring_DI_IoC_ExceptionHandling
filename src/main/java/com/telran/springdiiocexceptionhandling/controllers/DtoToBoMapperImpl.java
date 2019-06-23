package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DtoToBoMapperImpl implements DtoToBusinessObjMapper {

    @Override
    public Map<String, String> topicDtoToBo(TopicDto topicDto) {
        HashMap<String, String> res = new HashMap<>();
        res.put("author", topicDto.getAuthor());
        res.put("title", topicDto.getTitle());
        res.put("content", topicDto.getContent());
        return res;
    }

    @Override
    public TopicResponseDto topicBoToDto(Map<String, Object> topicBo) {
        return TopicResponseDto.topicResponseBuilder()
                .author(topicBo.get("author").toString())
                .content(topicBo.get("content").toString())
                .id(topicBo.get("id").toString())
                .title(topicBo.get("title").toString())
                .date(LocalDateTime.parse(topicBo.get("date").toString()))
                .build();
    }

    @Override
    public TopicFullDto topicBoToFullDto(Map<String, Object> topicFullBo) {
        return null;
    }

    @Override
    public Map<String, String> commentAddDtotoBo(AddCommentDto addCommentDto) {
        return null;
    }

    @Override
    public CommentFullDto commentBoToFullDto(Map<String, Object> commentFullDto) {
        return null;
    }

    @Override
    public Map<String, String> commentUpdDtoToBo(UpdateCommentDto updateCommentDto) {
        return null;
    }
}
