package com.fengquanwei.framework.helper;

import com.fengquanwei.framework.bean.FileParam;
import com.fengquanwei.framework.bean.FormParam;
import com.fengquanwei.framework.bean.Param;
import com.fengquanwei.framework.util.FileUtil;
import com.fengquanwei.framework.util.StreamUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 上传助手
 *
 * @author fengquanwei
 * @create 2017/12/6 15:51
 **/
public class UploadHelper {
    private static final Logger logger = LoggerFactory.getLogger(UploadHelper.class);

    // Apache Commons FileUpload 提供的 Servlet 文件上传对象
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempDir"); // 应用服务器的临时目录
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit(); // 上传文件的最大限制
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为 multipart 类型
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        List<FileParam> fileParamList = new ArrayList<>();

        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (fileItemListMap != null && fileItemListMap.size() > 0) {
                for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
                    String fieldName = fileItemListEntry.getKey();
                    List<FileItem> fileItemList = fileItemListEntry.getValue();
                    if (fileItemList != null && fileItemList.size() > 0) {
                        for (FileItem fileItem : fileItemList) {
                            if (fileItem.isFormField()) {
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            } else {
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtils.isNotEmpty(fileName)) {
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            logger.error("创建请求对象异常", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("创建请求对象异常", e);
        } catch (IOException e) {
            logger.error("创建请求对象异常", e);
        }

        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        if (fileParam != null) {
            String filePath = basePath + fileParam.getFileName();
            FileUtil.createFile(filePath);
            try (BufferedInputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                 BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                StreamUtil.copyStream(inputStream, outputStream);
            } catch (FileNotFoundException e) {
                logger.error("上传文件异常", e);
            } catch (IOException e) {
                logger.error("上传文件异常", e);
            }
        }
    }

    /**
     * 批量上传文件
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        if (fileParamList != null && fileParamList.size() > 0) {
            for (FileParam fileParam : fileParamList) {
                uploadFile(basePath, fileParam);
            }
        }
    }


}
