package com.pintoss.auth.common.paging;

import java.util.List;
import lombok.Getter;


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
@Getter
public class PageResponse<T> {

    private List<T> items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResponse(List<T> items, int page, int size, long totalElements) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

}
