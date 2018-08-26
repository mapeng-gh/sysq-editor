package com.huasheng.sysq.editor.dao;

import java.util.List;

import com.huasheng.sysq.editor.model.Version;

public interface VersionDao {

	public List<Version> findAll();
}
