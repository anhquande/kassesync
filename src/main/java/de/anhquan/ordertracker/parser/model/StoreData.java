package de.anhquan.ordertracker.parser.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class StoreData {
	
	@XStreamAlias("StoreId")
	private String _storeId;
	
	@XStreamAlias("StoreName")
	private String _storeName;
	
	public String getStoreId() {
		return _storeId;
	}

	public StoreData storeId(String storeId) {
		this._storeId = storeId;
		return this;
	}

	public String getStoreName() {
		return _storeName;
	}

	public StoreData storeName(String storeName) {
		this._storeName = storeName;
		return this;
	}
	
}
