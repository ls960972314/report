package com.sypay.omp.per.service;

import javax.servlet.http.HttpSession;



public interface ReloadCacheService {
	boolean reloadCache(HttpSession session);
}
