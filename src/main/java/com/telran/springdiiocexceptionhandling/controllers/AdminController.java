package com.telran.springdiiocexceptionhandling.controllers;

import com.telran.springdiiocexceptionhandling.controllers.dto.RemoveCommentDto;
import com.telran.springdiiocexceptionhandling.controllers.dto.SuccessResponseDto;

public interface AdminController {

    SuccessResponseDto removeById(String id);

    SuccessResponseDto removeComment(RemoveCommentDto remCommentDto);
}
