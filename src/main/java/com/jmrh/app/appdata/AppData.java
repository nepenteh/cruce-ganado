package com.jmrh.app.appdata;

import java.util.HashMap;

public interface AppData {

	public String getName();
	public String getAuthor();
	public int getYear();
	public String getWeb();
	public String getWebUrl();
	public String getAuthorship();
	public HashMap<String, GeneralOption> getGeneralOptions();
	public boolean isMainScreen(String optionCode, String screenCode);
	public String getMainScreenName(String optionCode);
	public String getMainScreenLink(String optionCode);
	public String getScreenName(String optionCode, String screenCode);
	public String getEmptyMessage(String optionCode);
}
