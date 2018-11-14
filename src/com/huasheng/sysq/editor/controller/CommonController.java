package com.huasheng.sysq.editor.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huasheng.sysq.editor.util.LogUtils;

@Controller
@RequestMapping(value="/common")
public class CommonController {
	
	private static final String FTP_HOST = "ccpl.psych.ac.cn";
	private static final int FTP_PORT = 20020;
	private static final String FTP_USER = "anonymous";
	private static final String FTP_PASSWD = "";
	private static final String FTP_WORKING_DIR = "/sysq/";
	
	/**
	 * 下载录音
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/downloadAudio.do",method=RequestMethod.GET)
	public void downloadAudio(@RequestParam(value="interviewId") int interviewId , HttpServletResponse response) throws IOException {
		LogUtils.info(this.getClass(), "downloadAuto params : interviewId = {}",interviewId);
		
		//下载设置
		response.setHeader("Content-Disposition","attachment; filename=" + interviewId + ".zip");
		OutputStream os = response.getOutputStream();
		
		//FTP设置
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(FTP_HOST,FTP_PORT);
		ftpClient.login(FTP_USER, FTP_PASSWD);
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		ftpClient.changeWorkingDirectory(FTP_WORKING_DIR);
		LogUtils.info(this.getClass(), "downloadAuto[{}] : FTP settings ok",interviewId);
		
		//文件过滤
		FTPFile files[] = ftpClient.listFiles("",new FTPFileFilter() {
			@Override
			public boolean accept(FTPFile file) {
				if(file.isFile() && file.getName().startsWith(interviewId + "")) {
					return true;
				}
				return false;
			}
		});
		if(files == null || files.length == 0) {
			LogUtils.info(this.getClass(), "downloadAuto[{}] : no audio file",interviewId);
			os.write(new String("没有查询到该访谈对应的录音文件").getBytes("UTF-8"));
			ftpClient.logout();
			ftpClient.disconnect();
			os.close();
			return;
		}
		
		//压缩输出
		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(os);
		zos.setEncoding("UTF-8");
		for(FTPFile ftpFile : files) {
			ZipArchiveEntry zipEntry = new ZipArchiveEntry(ftpFile.getName());
			zos.putArchiveEntry(zipEntry);
			ftpClient.retrieveFile(ftpFile.getName(), zos);
			zos.closeArchiveEntry();
			LogUtils.info(this.getClass(), "downloadAuto[{}] : file download finished = {}",interviewId,ftpFile.getName());
		}
		
		//资源关闭
		zos.finish();
		zos.close();
		ftpClient.logout();
		ftpClient.disconnect();
		LogUtils.info(this.getClass(), "downloadAuto[{}] : finished",interviewId);
	}
}
