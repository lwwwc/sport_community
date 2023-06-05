package com.lwc.sportcommunity.hwkit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Create by LWC on 2023/5/25 17:02
 */
@Data
@ToString
@AllArgsConstructor
public class SportVo {
    private int steps;
    private double distance;
    private double caloriesTotal;
}
