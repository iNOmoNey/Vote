package top.theanything.forum.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName CommonReturnType.java
 * @Description
 * @createTime 2020年04月13日 20:25:00
 */

@Builder
@Getter
@Setter
public class CommonReturnType implements Serializable {
    private  Object data;
    private String status;


    public static CommonReturnType create(Object data){
        return CommonReturnType.create(data,"success");
    }

    public static CommonReturnType create(Object data,String status){
        CommonReturnType build = CommonReturnType.builder()
                                        .data(data)
                                        .status(status)
                                        .build();
        return build;
    }
}
