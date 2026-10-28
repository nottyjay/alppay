package com.aialphay.alppay.model;

import com.aialphay.alppay.ApiField;
import lombok.Data;

/***
* 分账账号的修改
*
* @author nottyjay
* @site https://www.aialphay.com
* @date 2025-12-06 15:54:03
*/
@Data
public class DivisionReceiverChangeReqModel extends AlppayObject {

    private static final long serialVersionUID = -3998573128290306948L;

    @ApiField("mchNo")
    private String mchNo;      // 商户号

    @ApiField("appId")
    private String appId;      // 应用ID

    /** 分账接收者ID   **/
    @ApiField("receiverId")
    private Long receiverId;

    /** 接收者账号别名 **/
    @ApiField("receiverAlias")
    private String receiverAlias;

    /** 组ID  **/
    @ApiField("receiverGroupId")
    private Long receiverGroupId;

    /** 分账关系类型（参考微信）， 如： SERVICE_PROVIDER 服务商等 **/
    @ApiField("relationType")
    private String relationType;

    /** 当选择自定义时，需要录入该字段。 否则为对应的名称 **/
    @ApiField("relationTypeName")
    private String relationTypeName;

    /** 渠道特殊信息 */
    @ApiField("channelExtInfo")
    private String channelExtInfo;

    /** 分账比例 **/
    @ApiField("divisionProfit")
    private String divisionProfit;
}
