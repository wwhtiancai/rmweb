package com.tmri.rfid.endpoints;

import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.share.frm.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Joey on 2016/12/25.
 */
@Controller
@RequestMapping("/be/image.rfid")
public class ImageEndPoint {

    private final static Logger LOG = LoggerFactory.getLogger(ImageEndPoint.class);

    @Resource
    protected RuntimeProperty runtimeProperty;

    /**
     * �ϴ�ͼƬ�ӿڣ����ϴ�ͼƬΨһ��ʶ��Ҳ�ɲ��ϴ������ϴ�Ψһ��ʶʱ���᷵�������ɵ�Ψһ��ʶ��
     * @param type
     * @param image
     * @param id
     * @return
     */
    @RequestMapping(params = "method=upload-picture", method = RequestMethod.POST)
    @ResponseBody
    public String uploadPicture(@RequestParam(value = "type") String type,
                                @RequestParam(value = "image") String image,
                                @RequestParam(value = "id", required = false) String id) {
        LOG.info(String.format("calling ------> /be/image.rfid?upload-picture type=%s, id=%s", type,id));
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            String basePath = "/picture/" + DateUtil.getSysDate() + "/";
            String fileName = UUID.randomUUID() + ".png";
            String imageStr = image;
            //Ĭ�ϴ���Ĳ��������͵Ȳ�����data:image/png;base64,
            if (StringUtils.isNotEmpty(imageStr)) {
                imageStr = imageStr.substring(imageStr.indexOf(",") + 1);
            }
            Boolean flag = GenerateImage(imageStr, runtimeProperty.getFileFolder() + "/" + basePath, fileName);
            if (flag) {
                resultMap.put("resultId", "00");
                resultMap.put("imagePath", basePath + fileName);
            } else {
                resultMap.put("resultId", "01");
                resultMap.put("resultMsg", "�ϴ�ʧ��");
            }
        } catch (Exception e) {
            LOG.error("calling ------> /be/image.rfid?upload-picture fail", e);
            resultMap.put("resultId", "99");
            resultMap.put("resultMsg", "�ϴ��쳣(" + e.getMessage() + ")");
        }
        String result = GsonHelper.getGson().toJson(resultMap);
        LOG.info("calling ------> /be/image.rfid?upload-webcam-picture result = " + result);
        return result;
    }

    @RequestMapping(params = "method=show", method = RequestMethod.GET)
    public void show(@RequestParam(value = "path") String path,
                     HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.setContentType("image/jpeg");
            String absolutePath = runtimeProperty.getFileFolder() + "/" + path;
            FileInputStream fis = new FileInputStream(absolutePath);
            OutputStream os = response.getOutputStream();
            try {
                int count = 0;
                byte[] buffer = new byte[1024 * 1024];
                while ((count = fis.read(buffer)) != -1)
                    os.write(buffer, 0, count);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null)
                    os.close();
                if (fis != null)
                    fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ����������base64�ַ���ת����ͼƬ
     *
     * @since 2016/5/24
     */
    private boolean GenerateImage(String imgStr, String filePath, String fileName) throws Exception {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64����
        byte[] b = decoder.decodeBuffer(imgStr);
        //���Ŀ¼�����ڣ��򴴽�
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //����ͼƬ
        OutputStream out = new FileOutputStream(filePath + fileName);
        out.write(b);
        out.flush();
        out.close();
        return true;
    }

    private String GetImageStr(String imgFilePath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
        byte[] data = null;

        // ��ȡͼƬ�ֽ�����
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
