package com.liyz.boot3.api.test.dto.export;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/11 19:26
 */
@Getter
@Setter
public class ExportDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8556474743882878296L;

    private List<String> companyIds;
}
