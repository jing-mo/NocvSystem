package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.NocvData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NocvDataView extends NocvData {
    private Integer page;
    private Integer limit;

}
