package io.github.mrergos.util;

import io.github.mrergos.entity.MemberNkso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair {
    private Integer first;
    private List<MemberNkso> second;
}
