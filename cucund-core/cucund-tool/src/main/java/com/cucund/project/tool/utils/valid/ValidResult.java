package com.cucund.project.tool.utils.valid;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ToString
public class ValidResult {
    private boolean success;
    private String message;

    public static ValidResult success() {
        ValidResult result = new ValidResult();
        result.success = true;
        result.message = "操作成功";
        return result;
    }

    public static ValidResult fail( String message) {
        ValidResult result = new ValidResult();
        result.success = false;
        result.message = message;
        return result;
    }

    public static ValidResult fail(List<String> errorMessages) {
        ValidResult result = new ValidResult();
        result.success = false;
        result.message = StringUtils.join(errorMessages, ",/r/n");
        return result;
    }

    public void throwException() {
        if (!this.success) {
            throw new ValidException(this.message);
        }
    }

    public ValidResult() {
    }
}
