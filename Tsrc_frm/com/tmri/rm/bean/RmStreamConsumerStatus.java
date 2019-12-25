package com.tmri.rm.bean;

public class RmStreamConsumerStatus {
	private String key;
	private long offset;
	private long lag;

	public RmStreamConsumerStatus() {

	}

	public RmStreamConsumerStatus(String key, long offset, long lag) {
		this.key = key;
		this.offset = offset;
		this.lag = lag;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getLag() {
		return lag;
	}

	public void setLag(long lag) {
		this.lag = lag;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
