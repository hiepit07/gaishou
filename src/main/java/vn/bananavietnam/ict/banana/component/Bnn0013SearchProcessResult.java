package vn.bananavietnam.ict.banana.component;

import java.util.Date;

/**
 * 
 * @author NghiaNguyen
 *
 */
public class Bnn0013SearchProcessResult {

    // processId
    private String processId;
    // processName
    private String processName;
    // lastUpdateDate
    private Date lastUpdateDate;
    

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	// Added 1 more attribute to hold the total number of items searched in DB
    private String searchDataTotalCounts;

    public String getSearchDataTotalCounts() {
        return searchDataTotalCounts;
    }

    public void setSearchDataTotalCounts(String searchDataTotalCounts) {
        this.searchDataTotalCounts = searchDataTotalCounts;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /*
     * public String getStatus() { return status; }
     * 
     * public void setStatus(String status) { this.status = status; }
     */

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}