package de.anhquan.ordertracker.parser.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("SubArticle")
public class SubArticle {
	
	@XStreamAlias("ArticleNo")
	private String _articleNo;
	
	@XStreamAlias("ArticleName")
	private String _articleName;
	
	@XStreamAlias("Count")
	private int _count;
	
	@XStreamAlias("Partion")
	private String _partion;
		
	@XStreamOmitField
	private long _price;
	@XStreamAlias("Price")
	private String strPrice;
	
	@XStreamAlias("Tax")
	private String strTax;
	@XStreamOmitField
	private int _tax;
	
	@XStreamAlias("Deposit")
	private String strDeposit;
	@XStreamOmitField	
	private long _deposit;
	
	@XStreamAlias("Comment")
	private String _comment;

	public String getArticleNo() {
		return _articleNo;
	}

	public SubArticle articleNo(String articleNo) {
		this._articleNo = articleNo;
		return this;
	}

	public String getArticleName() {
		return _articleName;
	}

	public SubArticle articleName(String articleName) {
		this._articleName = articleName;
		return this;
	}

	public String getPartion() {
		return _partion;
	}

	public SubArticle partion(String partion) {
		this._partion = partion;
		return this;
	}

	public int getCount() {
		return _count;
	}

	public SubArticle count(int count) {
		this._count = count;
		return this;
	}

	public long getPrice() {
		return _price;
	}

	public SubArticle price(long price) {
		this._price = price;
		this.strPrice = Money.format(price);
		return this;
	}

	public int getTax() {
		return _tax;
	}

	public SubArticle tax(int tax) {
		this._tax = tax;
		this.strTax = Money.format(tax);
		return this;
	}

	public long getDeposit() {
		return _deposit;
	}

	public SubArticle deposit(long deposit) {
		this._deposit = deposit;
		this.strDeposit = Money.format(deposit);
		return this;
	}

	public String getComment() {
		return _comment;
	}

	public SubArticle comment(String comment) {
		this._comment = comment;
		return this;
	}

}
