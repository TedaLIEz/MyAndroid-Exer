package com.hustascii.torrentdemo.beans;

public class Result {
	private String name;
	private String url;
	private String size;
	private String detail;
	private String download_url;
	
	public String getDownload_url() {
		return download_url;
	}
	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
	@Override
	public String toString() {
		return "Result [name=" + name + ", url=" + url + ", size=" + size + "]";
	}
	
}
