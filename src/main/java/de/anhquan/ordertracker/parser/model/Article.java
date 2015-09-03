package de.anhquan.ordertracker.parser.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
@XStreamAlias("Article")
public class Article {
	
	@XStreamAlias("ArticleNo")
	private String _articleNo;
	
	@XStreamAlias("ArticleName")
	private String _articleName;
	
	@XStreamAlias("ArticleSize")
	private String _articleSize;
	
	@XStreamAlias("Count")
	private int _count;
		
	@XStreamOmitField
	private long _price;
	
	@XStreamAlias("Price")
	private String strPrice;
	
	@XStreamAlias("Tax")
	private int _tax =7;
	
	@XStreamAlias("Comment")
	private String _comment;
	
	@XStreamAlias("SubArticleList")
	List<SubArticle> _subArticles;
	
	@XStreamOmitField
	private long _deposit;
	
	@XStreamAlias("Deposit")
	private String strDeposit;
	
	public Article(){
		this._subArticles = new ArrayList<SubArticle>();
	}

	public List<SubArticle> getSubArticles() {
		return _subArticles;
	}

	public String getArticleNo() {
		return _articleNo;
	}

	public Article articleNo(String articleNo) {
		this._articleNo = articleNo;
		return this;
	}

	public String getArticleName() {
		return _articleName;
	}

	public Article articleName(String articleName) {
		this._articleName = articleName;
		return this;
	}

	public String getArticleSize() {
		return _articleSize;
	}

	public Article articleSize(String articleSize) {
		this._articleSize = articleSize;
		return this;
	}

	public int getCount() {
		return _count;
	}

	public Article count(int count) {
		this._count = count;
		return this;
	}

	public long getPrice() {
		return _price;
	}

	public Article price(long price) {
		this._price = price;
		strPrice = Money.format(price);
		return this;
	}

	public int getTax() {
		return _tax;
	}

	public Article tax(int tax) {
		this._tax = tax;
		return this;
	}

	public long getDeposit() {
		return _deposit;
	}

	public Article deposit(long deposit) {
		this._deposit = deposit;
		this.strDeposit = Money.format(deposit);
		return this;
	}

	public String getComment() {
		return _comment;
	}

	public Article comment(String comment) {
		this._comment = comment;
		return this;
	}

	public Article addSubArticle(SubArticle subArticle) {
		this._subArticles.add(subArticle);
		return this;
	}

}
