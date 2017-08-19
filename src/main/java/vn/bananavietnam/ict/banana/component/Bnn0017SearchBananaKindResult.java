package vn.bananavietnam.ict.banana.component;

import java.util.Date;

/**
 * 
 * @author NghiaNguyen
 *
 */
public class Bnn0017SearchBananaKindResult {

    // Kind Id
    private String kindId;
    // Kind Name
    private String kindName;
    // prospectiveHarvestAmount
    private Double prospectiveHarvestAmount;
    // EstimatedDaysFlowering
    private String estimatedDaysFlowering;
    // EstimatedDaysFlowering
    private String estimatedDaysBagging;
    // EstimatedDaysFlowering
    private String estimatedDaysHarvest;
    // deleteFlag
    private String deleteFlag;
    //lastUpdateDate
    private Date lastUpdateDate;
    
    public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	// Added 1 more attribute to hold the total number of items searched in DB
    private String searchDataTotalCounts;

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getSearchDataTotalCounts() {
        return searchDataTotalCounts;
    }

    public void setSearchDataTotalCounts(String searchDataTotalCounts) {
        this.searchDataTotalCounts = searchDataTotalCounts;
    }

    public String getEstimatedDaysFlowering() {
        return estimatedDaysFlowering;
    }

    public void setEstimatedDaysFlowering(String estimatedDaysFlowering) {
        this.estimatedDaysFlowering = estimatedDaysFlowering;
    }

    public String getEstimatedDaysBagging() {
        return estimatedDaysBagging;
    }

    public void setEstimatedDaysBagging(String estimatedDaysBagging) {
        this.estimatedDaysBagging = estimatedDaysBagging;
    }

    public String getEstimatedDaysHarvest() {
        return estimatedDaysHarvest;
    }

    public void setEstimatedDaysHarvest(String estimatedDaysHarvest) {
        this.estimatedDaysHarvest = estimatedDaysHarvest;
    }

    public Double getProspectiveHarvestAmount() {
        return prospectiveHarvestAmount;
    }

    public void setProspectiveHarvestAmount(Double prospectiveHarvestAmount) {
        this.prospectiveHarvestAmount = prospectiveHarvestAmount;
    }
}