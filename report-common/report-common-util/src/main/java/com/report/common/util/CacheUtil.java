package com.report.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheUtil {

	static LoadingCache<String, Map<String, String>> cache;
	
	public static void main(String[] args) throws InterruptedException {
		log.info("1    cache.get()===>result[{}]", getParamMap().get(""));
		Thread.sleep(1000l);
		log.info("2    cache.get()===>result[{}]", getParamMap().get(""));
	}

	private static Map<String, String> getParamMap() {
		if (null == cache) {
			cache = CacheBuilder.newBuilder().expireAfterWrite(2l, TimeUnit.SECONDS).
					maximumSize(1).build(new CacheLoader<String, Map<String, String>>() {
						@Override
						public Map<String, String> load(String key) throws Exception {
							log.info("==============enter load===============");
							Map<String, String> map = new HashMap<>();
							map.put("a", "111");
							map.put("b", "222");
							map.put("c", "333");
							return map;
						}
					});
		}
		
		try {
			return cache.get("");
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return new HashMap<>();
	}
	
	
}
