package com.aialphay.alppay.mgr.ctrl.common;

import com.aialphay.alppay.components.oss.config.OssYmlConfig;
import com.aialphay.alppay.mgr.ctrl.CommonCtrl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 静态文件下载/预览 ctrl
 *
 * @Author nottyjay
 * @site https://www.aialphay.com
 * @date 2021/6/8 17:08
 */
@Controller
public class StaticController extends CommonCtrl {

  @Autowired private OssYmlConfig ossYmlConfig;

  /** 图片预览 * */
  @GetMapping("/api/anon/localOssFiles/*/*.*")
  public ResponseEntity<?> imgView() {

    try {

      // 查找图片文件
      File imgFile =
          new File(
              ossYmlConfig.getOss().getFilePublicPath()
                  + File.separator
                  + request.getRequestURI().substring(24));
      if (!imgFile.isFile() || !imgFile.exists()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      // 输出文件流（图片格式）
      HttpHeaders httpHeaders = new HttpHeaders();
      //            httpHeaders.setContentType(MediaType.IMAGE_JPEG);  //图片格式
      InputStream inputStream = new FileInputStream(imgFile);
      return new ResponseEntity<>(new InputStreamResource(inputStream), httpHeaders, HttpStatus.OK);

    } catch (FileNotFoundException e) {
      logger.error("static file error", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
