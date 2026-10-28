package com.aialphay.alppay.pay.ctrl.scanimg;

import com.aialphay.alppay.core.utils.AlppayKit;
import com.aialphay.alppay.pay.ctrl.payorder.AbstractPayOrderController;
import com.aialphay.alppay.pay.util.CodeImgUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * alipay 扫描图片生成器
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:28
 */
@RestController
@RequestMapping("/api/scan")
public class ScanImgController extends AbstractPayOrderController {

  /** 返回 图片地址信息 * */
  @RequestMapping("/imgs/{aesStr}.png")
  public void qrImgs(@PathVariable("aesStr") String aesStr) throws Exception {
    String str = AlppayKit.aesDecode(aesStr);
    int width = getValIntegerDefault("width", 200);
    int height = getValIntegerDefault("height", 200);
    CodeImgUtil.writeQrCode(response.getOutputStream(), str, width, height);
  }
}
