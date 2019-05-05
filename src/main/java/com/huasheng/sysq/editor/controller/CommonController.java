package com.huasheng.sysq.editor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huasheng.sysq.editor.util.LogUtils;

@Controller
@RequestMapping(value="/common")
public class CommonController {
	
	private static final String MSG_CONTENT_TYPE = "text/plain;charset=utf-8";
	
	@Value("${ftp.host}")
	private String FTP_HOST;
	
	@Value("${ftp.port}")
	private int FTP_PORT;
	
	@Value("${ftp.user}")
	private String FTP_USER;
	
	@Value("${ftp.passwd}")
	private String FTP_PASSWD;
	
	@Value("${ftp.workingDir}")
	private String FTP_WORKING_DIR;
	
	/**
	 * 下载录音
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/downloadAudio.do",method=RequestMethod.GET)
	public void downloadAudio(@RequestParam(value="interviewId") final int interviewId , HttpServletResponse response) {
		LogUtils.info(this.getClass(), "downloadAuto params : interviewId = {}",interviewId);
		
		FTPClient ftpClient = null;
		ZipArchiveOutputStream zos = null;
		try {
			//FTP设置
			ftpClient = new FTPClient();
			ftpClient.connect(FTP_HOST,FTP_PORT);
			ftpClient.login(FTP_USER, FTP_PASSWD);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalActiveMode();
			ftpClient.setRemoteVerificationEnabled(false);
			ftpClient.changeWorkingDirectory(FTP_WORKING_DIR);
			LogUtils.info(this.getClass(), "downloadAuto[{}] : FTP settings ok , {}:{}",interviewId,FTP_HOST,FTP_PORT);
			
			//文件过滤
			FTPFile files[] = ftpClient.listFiles("",new FTPFileFilter() {
				@Override
				public boolean accept(FTPFile file) {
					if(file != null && file.isFile() && file.getName().startsWith(interviewId + "")) {
						return true;
					}
					return false;
				}
			});
			if(files == null || files.length == 0) {
				LogUtils.info(this.getClass(), "downloadAuto[{}] : no audio file",interviewId);
				response.setContentType(MSG_CONTENT_TYPE);
				response.getWriter().write("没有查询到该访谈对应的录音文件");
				return;
			}
			
			//下载设置
			response.setHeader("Content-Disposition","attachment; filename=" + interviewId + ".zip");
			
			//压缩输出
			zos = new ZipArchiveOutputStream(response.getOutputStream());
			zos.setEncoding("UTF-8");
			for(FTPFile ftpFile : files) {
				ZipArchiveEntry zipEntry = new ZipArchiveEntry(ftpFile.getName());
				zos.putArchiveEntry(zipEntry);
				ftpClient.retrieveFile(ftpFile.getName(), zos);
				zos.closeArchiveEntry();
				LogUtils.info(this.getClass(), "downloadAuto[{}] : file download finished = {}",interviewId,ftpFile.getName());
			}
			zos.finish();
			LogUtils.info(this.getClass(), "downloadAuto[{}] : finished",interviewId);
			
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "downloadAuto["+interviewId+"] error", e);
			try {
				response.setContentType(MSG_CONTENT_TYPE);
				response.getWriter().write("下载录音文件失败");
			}catch(Exception e1) {}
			
		}finally {
			try {
				if(zos != null) {
					zos.close();
				}
				if(ftpClient != null) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}catch(Exception e) {}
		}
	}
}
