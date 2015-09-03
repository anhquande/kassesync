package de.anhquan.ordertracker.parser.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ServerData {
	
	@XStreamAlias("IpAddress")
	private String _ipAdress;
	
	@XStreamAlias("Agent")
	private String _agent;
	
	@XStreamAlias("CreateDateTime")
	private Date _createDateTime;
	
	@XStreamAlias("Referer")
	private String _referer;
	
	public ServerData(){
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public String getIpAdress() {
		return _ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this._ipAdress = ipAdress;
	}

	public String getAgent() {
		return _agent;
	}

	public void setAgent(String agent) {
		this._agent = agent;
	}
	
	public String getReferer() {
		return _referer;
	}

	public void setReferer(String referer) {
		this._agent = referer;
	}

	public Date getCreateDateTime() {
		return _createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this._createDateTime = createDateTime;
	}

	public ServerData agent(String agent) {
		this._agent = agent;
		return this;
	}
	
	public ServerData ipAddress(String ipAdress) {
		this._ipAdress = ipAdress;
		return this;
	}
	
	public ServerData referer(String referer) {
		this._referer = referer;
		return this;
	}
	
}
