package com.rapidquotation.entities.mechanical;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rapidquotation.entities.Company;
import com.rapidquotation.entities.DecorationItems;

@Entity
@Table
public class DecorationQuote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int decorationId;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Company_id")
	private Company company;

	private Long quotationNumber;
	
	private Date creationDate;
	
	private Date expieryDate;
	
	private String title;
	
	private String description;
	
	private String functionName;
	
	private int labourCost;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "decoration_quote_id",referencedColumnName = "decorationId")
	private List<DecorationItems> decorationItems;
	
	private String websiteLink;
	
	private long faxNumber;
	
	private String approxTime;
	
	private String termsAndConditions;
	
	private long total;
	
	private long totalIncludingAllCosts;
	
	private String gst;
	
	private long totalIncludingAllCostsWithGst;
	
	
	
	
	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public long getTotalIncludingAllCostsWithGst() {
		return totalIncludingAllCostsWithGst;
	}

	public void setTotalIncludingAllCostsWithGst(long totalIncludingAllCostsWithGst) {
		this.totalIncludingAllCostsWithGst = totalIncludingAllCostsWithGst;
	}

	public long getTotalIncludingAllCosts() {
		return totalIncludingAllCosts;
	}

	public void setTotalIncludingAllCosts(long totalIncludingAllCosts) {
		this.totalIncludingAllCosts = totalIncludingAllCosts;
	}


	public int getDecorationId() {
		return decorationId;
	}

	public void setDecorationId(int decorationId) {
		this.decorationId = decorationId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Long getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(Long quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpieryDate() {
		return expieryDate;
	}

	public void setExpieryDate(Date expieryDate) {
		this.expieryDate = expieryDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public int getLabourCost() {
		return labourCost;
	}

	public void setLabourCost(int labourCost) {
		this.labourCost = labourCost;
	}

	public List<DecorationItems> getDecorationItems() {
		if(decorationItems == null)
		{
			decorationItems = new ArrayList<>();
		}
		return decorationItems;
	}

	public void setDecorationItems(List<DecorationItems> decorationItems) {
		this.decorationItems = decorationItems;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

	public long getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(long faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getApproxTime() {
		return approxTime;
	}

	public void setApproxTime(String approxTime) {
		this.approxTime = approxTime;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	
}
