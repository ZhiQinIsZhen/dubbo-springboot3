package com.liyz.boot3.api.test.controller.export;

import cn.idev.excel.EasyExcel;
import com.liyz.boot3.api.test.dto.export.ExportDTO;
import com.liyz.boot3.api.test.excel.SscStaffExcel;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.vo.export.SscPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/12 9:47
 */
@Slf4j
@Tag(name = "导出")
@RestController
@RequestMapping("/export")
public class ExportController {

    @Resource
    private RestClient restClient;

    @Operation(summary = "主要人员导出")
    @PostMapping("/staff")
    public Result<Boolean> exportStaff(@RequestBody ExportDTO exportDTO) {
        List<String> companyNames = exportDTO.getCompanyIds().stream().map(String::trim).distinct().toList();
        EasyExcel.write(this.getFileKey(), SscStaffExcel.class).sheet("主要人员")
                .doWrite(() -> {
                    List<SscStaffExcel> boList = new ArrayList<>();
                    Map<String, Object> params = new HashMap<>();
                    params.put("pageNum", 1);
                    params.put("pageSize", 20);
                    for (String companyName : companyNames) {
                        params.put("keyword", companyName);
                        ResponseEntity<SscPageVO<SscStaffExcel>> responseEntity = restClient.get()
                                .uri("https://openapi.sscha.com/services/ic/staff?keyword={keyword}&pageNum={pageNum}&pageSize={pageSize}", params)
                                .header("Authorization", "736fe7a1-6bc4-4663-b1f3-fc99aa1af2b0")
                                .retrieve()
                                .toEntity(new ParameterizedTypeReference<>() {});
                        SscPageVO<SscStaffExcel> sscPageVO = responseEntity.getBody();
                        if ("0".equals(sscPageVO.getCode())) {
                            log.info("{} count {}", companyName, sscPageVO.getData().getTotal());
                            if (sscPageVO.getData().getTotal() > 0) {
                                boList.addAll(sscPageVO.getData().getItems());
                            }
                        }
                    }
                    return boList;
                });



        return Result.success(Boolean.TRUE);
    }

    private String getFileKey() {
        return "C:\\Users\\liyangzhen\\Downloads\\excel\\" + System.currentTimeMillis() + ".xlsx";
    }
}
