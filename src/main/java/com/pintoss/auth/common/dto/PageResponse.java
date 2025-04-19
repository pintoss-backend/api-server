package com.pintoss.auth.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


/*
    Page<User> userPage = userRepository.findAll(pageable);

    PageResponse<UserDto> response = new PageResponse<>(
        userPage.getContent().stream().map(UserDto::from).toList(),
        userPage.getNumber(),
        userPage.getSize(),
        userPage.getTotalElements(),
        userPage.getTotalPages()
    );
*/
@Data
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}
