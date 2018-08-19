package com.huasheng.sysq.editor.util;

public class Constants {

	//用户类型（1：管理员 2、编辑人员 3、浏览人员）
	public static final int USER_TYPE_ADMIN = 1;
	public static final int USER_TYPE_EDITOR = 2;
	public static final int USER_TYPE_VIEWER = 3;
	
	//审核状态（1：审核中 2：审核通过 3：审核不通过）
	public static final int AUDIT_STATUS_ING = 1;
	public static final int AUDIT_STATUS_PASS = 2;
	public static final int AUDIT_STATUS_REJECT = 3;
	
	//访谈类型（1：病例	2、对照）
	public static final int INTERVIEW_TYPE_CASE = 1;
	public static final int INTERVIEW_TYPE_CONTRAST = 2;
}
