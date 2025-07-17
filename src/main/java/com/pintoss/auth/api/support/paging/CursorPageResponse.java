package com.pintoss.auth.api.support.paging;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/*
    // 예: where id < :cursor order by id desc limit 10
    List<User> users = userRepository.findByIdLessThanOrderByIdDesc(cursor, size);

    String nextCursor = users.size() < size ? null : String.valueOf(users.get(users.size() - 1).getId());

    CursorPageResponse<UserDto> response = CursorPageResponse.<UserDto>builder()
        .content(users.stream().map(UserDto::from).toList())
        .nextCursor(nextCursor)
        .hasNext(nextCursor != null)
        .build();
*/
@Data
@Builder
@AllArgsConstructor
public class CursorPageResponse<T> {

    private List<T> content;
    private String nextCursor;
    private boolean hasNext;

}
