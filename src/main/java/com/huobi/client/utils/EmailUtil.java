package com.huobi.client.utils;

import io.github.biezhi.ome.OhMyEmail;
import static io.github.biezhi.ome.OhMyEmail.SMTP_163;

public class EmailUtil {
	private final static EmailUtil instance = new EmailUtil();

	private EmailUtil() {
		OhMyEmail.config(SMTP_163(false), "gongran_ok@163.com", "gongran2019");
	}

	public static EmailUtil getInstance() {
		return instance;
	}

}
