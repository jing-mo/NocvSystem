package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.HealthClock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class HealthClockVo extends HealthClock {
    private Integer page=1;
    private Integer limit=10;

    //分页


    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }
}
