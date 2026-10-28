package com.aialphay.alppay.model;

import com.aialphay.alppay.ApiField;
import lombok.Data;

/***
* 分账账号的删除
*
* @author nottyjay
* @site https://www.aialphay.com
* @date  2025-12-06 15:54:03
*/
@Data
public class DivisionReceiverDeleteReqModel extends AlppayObject {

    private static final long serialVersionUID = -3998573128290306948L;

    @ApiField("mchNo")
    private String mchNo;      // 商户号

    @ApiField("appId")
    private String appId;      // 应用ID

    /** 分账接收者ID   **/
    @ApiField("receiverId")
    private Long receiverId;

}
