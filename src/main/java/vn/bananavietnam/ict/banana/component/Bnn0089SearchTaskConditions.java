package vn.bananavietnam.ict.banana.component;

/**
 * 
 * @author NghiaNguyen
 *
 */
public class Bnn0089SearchTaskConditions {

    // taskId_ID
    private String taskId;

    // task_Name
    private String taskName;

    // From parameter
    private String fromRow;

    // Number of items in a page
    private String itemCount;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

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
}