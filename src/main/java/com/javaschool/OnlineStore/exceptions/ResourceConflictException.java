package com.javaschool.OnlineStore.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResourceConflictException extends RuntimeException{
    private final String message;
}
