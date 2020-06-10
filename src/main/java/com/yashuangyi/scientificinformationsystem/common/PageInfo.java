package com.yashuangyi.scientificinformationsystem.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-25 8:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    Long total;
    List rows;
}
