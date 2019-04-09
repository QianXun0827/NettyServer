package com.pm.cms.common.util;

import java.util.UUID;

public final class UuidUtil {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getUUID(String param) {
		return UUID.nameUUIDFromBytes(param.getBytes()).toString().replaceAll("-", "");
	}

}