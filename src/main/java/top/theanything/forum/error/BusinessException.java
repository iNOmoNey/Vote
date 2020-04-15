package top.theanything.forum.error;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName BusinessException.java
 * @Description
 * @createTime 2020年04月13日 20:48:00
 */
public class BusinessException extends Exception  implements CommonError  {
    private CommonError commonError;

    public BusinessException(CommonError commonError){
        this.commonError = commonError;
    }

    @Override
    public int getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }
}
