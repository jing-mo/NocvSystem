package com.exia.nocvsystem.vo;

import com.exia.nocvsystem.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuView extends Menu {
    private Integer page;
    private Integer limit;
}
