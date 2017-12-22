package com.example.demo.controller;

	import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

	/**
	 * 文件上传的Controller
	 * 
	 * @author 单红宇(CSDN CATOOP)
	 * @create 2017年3月11日
	 */
	@Controller
	public class FileUploadController {

	    // 访问路径为：http://ip:port/upload
	    @RequestMapping(value = "/upload", method = RequestMethod.GET)
	    public String upload() {
	        return "/fileupload";
	    }

	    // 访问路径为：http://ip:port/upload/batch
	    @RequestMapping(value = "/upload/batch", method = RequestMethod.GET)
	    public String batchUpload() {
	        return "/mutifileupload";
	    }

	    /**
	     * 文件上传具体实现方法（单文件上传）
	     *
	     * @param file
	     * @return
	     * 
	     * @author 单红宇(CSDN CATOOP)
	     * @create 2017年3月11日
	     */
	    @RequestMapping(value = "/upload", method = RequestMethod.POST)
	    @ResponseBody
	    public String upload(@RequestParam("file") MultipartFile file) {
	        if (!file.isEmpty()) {
	            try {
	                // 这里只是简单例子，文件直接输出到项目路径下。
	                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
	                // 还有关于文件格式限制、文件大小限制，详见：中配置。
	                BufferedOutputStream out = new BufferedOutputStream(
	                        new FileOutputStream(new File("temp/"+file.getOriginalFilename())));
	                //http://img30.360buyimg.com/shaidan/s616x405_jfs/t7006/340/2419749076/215144/664e324b/598c2ee6N62cc68e4.jpg
	                out.write(file.getBytes());
	                out.flush();
	                out.close();
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	                return "上传失败," + e.getMessage();
	            } catch (IOException e) {
	                e.printStackTrace();
	                return "上传失败," + e.getMessage();
	            }
	            return "上传成功";
	        } else {
	            return "上传失败，因为文件是空的.";
	        }
	    }
	    
	    @RequestMapping(value = "/uploadurl", method = RequestMethod.GET)
	    @ResponseBody
	    public String uploadurl() throws Exception {
	    	URL url = new URL("http://img30.360buyimg.com/shaidan/s616x405_jfs/t7006/340/2419749076/215144/664e324b/598c2ee6N62cc68e4.jpg");    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	                //设置超时间为3秒  
	        conn.setConnectTimeout(3*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	  
	        //得到输入流  
	        InputStream inputStream = conn.getInputStream();    
	        //获取自己数组  
	        byte[] getData = readInputStream(inputStream);      
	  
	        //文件保存位置  
	        File saveDir = new File("temp");  
	        if(!saveDir.exists()){  
	            saveDir.mkdir();  
	        }  
	        File file = new File(saveDir+File.separator+"001.jpg");      
	        FileOutputStream fos = new FileOutputStream(file);       
	        fos.write(getData);   
	        if(fos!=null){  
	            fos.close();    
	        }  
	        if(inputStream!=null){  
	            inputStream.close();  
	        }  
	        System.out.println("info:"+url+" download success");   
	        return "上传.";
	    }

	    private byte[] readInputStream(InputStream inputStream) throws Exception {
	    	byte[] buffer = new byte[1024];    
	        int len = 0;    
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	        while((len = inputStream.read(buffer)) != -1) {    
	            bos.write(buffer, 0, len);    
	        }    
	        bos.close();    
	        return bos.toByteArray();    
		}

		/**
	     * 多文件上传 主要是使用了MultipartHttpServletRequest和MultipartFile
	     *
	     * @param request
	     * @return
	     * 
	     * @author 单红宇(CSDN CATOOP)
	     * @create 2017年3月11日
	     */
	    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
	    public @ResponseBody String batchUpload(HttpServletRequest request) {
	        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	        MultipartFile file = null;
	        BufferedOutputStream stream = null;
	        for (int i = 0; i < files.size(); ++i) {
	            file = files.get(i);
	            if (!file.isEmpty()) {
	                try {
	                    byte[] bytes = file.getBytes();
	                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
	                    stream.write(bytes);
	                    stream.close();
	                } catch (Exception e) {
	                    stream = null;
	                    return "You failed to upload " + i + " => " + e.getMessage();
	                }
	            } else {
	                return "You failed to upload " + i + " because the file was empty.";
	            }
	        }
	        return "upload successful";
	    }
}
