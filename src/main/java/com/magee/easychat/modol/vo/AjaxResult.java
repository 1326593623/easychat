package com.magee.easychat.modol.vo;

import lombok.Data;

/**
 *  Ajax消息实体封装
 */
@Data
public class AjaxResult {
    private boolean successed;
    private Integer error_code;

    public AjaxResult(boolean successed) {
        this.successed = successed;
    }
    public AjaxResult(boolean successed, Integer error_code) {
        this.successed = successed;
        this.error_code = error_code;
    }


}
