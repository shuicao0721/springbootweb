package com.example.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller  
public class BackIndexController {  
  
    @RequestMapping(value = "/",method = RequestMethod.GET)  
    public String index() {  
        return "backindex";  
    }  
    
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("name", "Dear");
        return "hello";
    }
    
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("name", "Dear");
        return "login";
    }
    
    @RequestMapping("/ibeLogin")
	public ModelAndView ibeLogin(@RequestParam(value = "error", required = false) String error , 
			@RequestParam(value = "accessDeny", required = false) String accessDeny , 
			HttpServletRequest request , Exception ex) {

		ModelAndView model = new ModelAndView();
			
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		
		if (accessDeny != null) {
			model.addObject("error", "Access Deny , you have no permission to access !");
		}

		model.setViewName("hello");
		return model;
	}
    
    @RequestMapping(value="/downloadmcode",method=RequestMethod.POST)  
    public void downloadMcode(HttpServletResponse response, String score, String productId){  
    	System.out.println("-------------------------------score="+score);  
    	System.out.println("-------------------------------productId="+productId); 
        try {  
            String filePath = "temp/facade_pom.xml";
        	String fileName = "facade_pom.xml";// 文件名称  
            System.out.println("-------------------------------上传文件名为="+fileName);  
              
            //下载机器码文件  
            response.setHeader("conent-type", "application/octet-stream");  
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));  
              
            OutputStream os = response.getOutputStream();  
            BufferedOutputStream bos = new BufferedOutputStream(os);  
              
            InputStream is = null;  
  
            is = new FileInputStream(filePath);  
            BufferedInputStream bis = new BufferedInputStream(is);  
  
            int length = 0;  
            byte[] temp = new byte[1 * 1024 * 10];  
  
            while ((length = bis.read(temp)) != -1) {  
                bos.write(temp, 0, length);  
            }  
            bos.flush();  
            bis.close();  
            bos.close();  
            is.close();           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    @RequestMapping(value="/downloadzip",method=RequestMethod.GET)  
    public void downloadzip(HttpServletResponse response) throws Exception{  
    	// 要被压缩的文件夹  
        File file = new File("temp");  
        File zipFile = new File("tempZip/zipFile.zip");  
        InputStream input = null;  
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));  
        zipOut.setComment("hello");  
        if(file.isDirectory()){  
            File[] files = file.listFiles();  
            for(int i = 0; i < files.length; ++i){  
                input = new FileInputStream(files[i]);  
                zipOut.putNextEntry(new ZipEntry(file.getName()  
                        + File.separator + files[i].getName()));  
                int temp = 0;  
                while((temp = input.read()) != -1){  
                    zipOut.write(temp);  
                }  
                input.close();  
            }  
        }  
        zipOut.flush();
        zipOut.close(); 
        try {  
            String filePath = "tempZip/zipFile.zip";
        	String fileName = "zipFile.zip";// 文件名称  
            System.out.println("-------------------------------上传文件名为="+fileName);  
              
            //下载机器码文件  
            response.setHeader("conent-type", "application/octet-stream");  
            response.setContentType("application/octet-stream");  
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));  
              
            OutputStream os = response.getOutputStream();  
            BufferedOutputStream bos = new BufferedOutputStream(os);  
              
            InputStream is = null;  
  
            is = new FileInputStream(filePath);  
            BufferedInputStream bis = new BufferedInputStream(is);  
  
            int length = 0;  
            byte[] temp = new byte[1 * 1024 * 10];  
  
            while ((length = bis.read(temp)) != -1) {  
                bos.write(temp, 0, length);  
            }  
            bos.flush();  
            bis.close();  
            bos.close();  
            is.close();           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
      
}  
