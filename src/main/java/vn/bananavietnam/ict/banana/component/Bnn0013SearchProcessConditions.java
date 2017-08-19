package vn.bananavietnam.ict.banana.component;

/**
 * 
 * @author NghiaNguyen
 *
 */
public class Bnn0013SearchProcessConditions {

    // processId
    private String processId;

    // processName
    private String processName;

	// From parameter
    private String fromRow;

    // Number of items in a page
    private String itemCount;

    public String getFromRow() {
        return fromRow;
    }

    public void setFromRow(String fromRow) {
        this.fromRow = fromRow;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}