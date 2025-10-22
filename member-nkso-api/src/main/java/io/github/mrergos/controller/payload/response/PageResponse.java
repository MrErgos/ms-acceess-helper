package io.github.mrergos.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private int totalElements;
    private int size;
    private int number;
    private boolean first;
    private boolean last;
}
