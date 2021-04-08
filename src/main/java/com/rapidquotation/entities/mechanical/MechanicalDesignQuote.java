package com.rapidquotation.entities.mechanical;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.rapidquotation.entities.Company;
import com.rapidquotation.entities.QuoteItems;


@Entity
@Table(name = "mechanical_design_Quote")
public class MechanicalDesignQuote {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mechDesignQuoteId;
	
	
	private int quotationNumber;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creationDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date expieryDate;

	private String websiteLink;
	
	private long faxNumber;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Company company;
	
	private String quoteTitle;
	
	private String quoteDescription;
	
	private int labourCost;
	
	private int setupCost;
	
	private int engineeringCost;
	
	private int toolingAndConsumablesCost;
	
	private int outsideServicesAndProcessesCost;
	
	private String maintainanceContractPeriod;
	
	private int maintainanceCost;
	
	private int transportationCost;
	
	private int packagingCost;
	
	private Long totalIncludingAllCosts;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "mech_design_quote_id",referencedColumnName = "mechDesignQuoteId")
	private List<QuoteItems> mechItems;
	
	private String approxTime;
	
	private String termsAndConditions;
	
	private long total;
	
	private String gst;
	
	private long totalIncludingAllCostsWithGst;
	
	
	public int getQuotationNumber() {
		return quotationNumber;
	}
	public void setQuotationNumber(int quotationNumber) {
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
	
	
	public String getQuoteTitle() {
		return quoteTitle;
	}
	public void setQuoteTitle(String quoteTitle) {
		this.quoteTitle = quoteTitle;
	}
	public String getQuoteDescription() {
		return quoteDescription;
	}
	public void setQuoteDescription(String quoteDescription) {
		this.quoteDescription = quoteDescription;
	}
	
	
	
	
	
	public int getLabourCost() {
		return labourCost;
	}
	public void setLabourCost(int labourCost) {
		this.labourCost = labourCost;
	}
	public Long getTotalIncludingAllCosts() {
		return totalIncludingAllCosts;
	}
	public void setTotalIncludingAllCosts(Long totalIncludingAllCosts) {
		this.totalIncludingAllCosts = totalIncludingAllCosts;
	}
	public int getSetupCost() {
		return setupCost;
	}
	public void setSetupCost(int setupCost) {
		this.setupCost = setupCost;
	}
	public int getEngineeringCost() {
		return engineeringCost;
	}
	public void setEngineeringCost(int engineeringCost) {
		this.engineeringCost = engineeringCost;
	}
	public int getToolingAndConsumablesCost() {
		return toolingAndConsumablesCost;
	}
	public void setToolingAndConsumablesCost(int toolingAndConsumablesCost) {
		this.toolingAndConsumablesCost = toolingAndConsumablesCost;
	}
	public int getOutsideServicesAndProcessesCost() {
		return outsideServicesAndProcessesCost;
	}
	public void setOutsideServicesAndProcessesCost(int outsideServicesAndProcessesCost) {
		this.outsideServicesAndProcessesCost = outsideServicesAndProcessesCost;
	}
	public String getMaintainanceContractPeriod() {
		return maintainanceContractPeriod;
	}
	public void setMaintainanceContractPeriod(String maintainanceContractPeriod) {
		this.maintainanceContractPeriod = maintainanceContractPeriod;
	}
	
	
	public int getMaintainanceCost() {
		return maintainanceCost;
	}
	public void setMaintainanceCost(int maintainanceCost) {
		this.maintainanceCost = maintainanceCost;
	}
	public int getTransportationCost() {
		return transportationCost;
	}
	public void setTransportationCost(int transportationCost) {
		this.transportationCost = transportationCost;
	}
	public int getPackagingCost() {
		return packagingCost;
	}
	public void setPackagingCost(int packagingCost) {
		this.packagingCost = packagingCost;
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
	public List<QuoteItems> getMechItems() {
		if(mechItems == null)
		{
			mechItems = new ArrayList<>();
		}
		
		return mechItems;
	}
	public void setMechItems(List<QuoteItems> mechItems) {
		this.mechItems = mechItems;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getMechDesignQuoteId() {
		return mechDesignQuoteId;
	}
	public void setMechDesignQuoteId(int mechDesignQuoteId) {
		this.mechDesignQuoteId = mechDesignQuoteId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
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
}
