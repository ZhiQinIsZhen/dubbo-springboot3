package com.liyz.boot3.api.test.controller.shardingjdbc;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liyz.boot3.api.test.dto.shardingjdbc.UserDTO;
import com.liyz.boot3.api.test.dto.shardingjdbc.UserLoginLogDTO;
import com.liyz.boot3.api.test.model.UserLoginLogDO;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.service.UserLoginLogService;
import com.liyz.boot3.api.test.util.BeanUtil;
import com.liyz.boot3.api.test.vo.shardingjdbc.UserLoginLogVO;
import com.liyz.boot3.common.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/19 9:41
 */
@Slf4j
@Tag(name = "shardingJdbc")
@RestController
@RequestMapping("/sharding/jdbc")
public class ShardingJdbcController {

    @Resource
    private UserLoginLogService userLoginLogService;

    @Operation(summary = "通过userId查询")
    @GetMapping("/getByUserId")
    public Result<List<UserLoginLogVO>> hello(UserDTO userDTO) {
        UserLoginLogDO userLoginLogDO = new UserLoginLogDO();
        userLoginLogDO.setUserId(userDTO.getUserId());
        List<UserLoginLogDO> list = userLoginLogService.list(Wrappers.lambdaQuery(userLoginLogDO));
        return Result.success(BeanUtil.copyList(list, UserLoginLogVO::new));
    }

    @Operation(summary = "增加登录日志")
    @PostMapping("/addLogin")
    public Result<Boolean> addLogin(@RequestBody UserLoginLogDTO userLoginLogDTO) {
        UserLoginLogDO userLoginLogDO = BeanUtil.copyProperties(userLoginLogDTO, UserLoginLogDO::new, (s, t) -> {
            t.setLoginTime(DateUtil.currentDate());
        });
        userLoginLogService.save(userLoginLogDO);
        return Result.success(Boolean.TRUE);
    }
}
