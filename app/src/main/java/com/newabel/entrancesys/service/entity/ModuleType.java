package com.newabel.entrancesys.service.entity;

/**
 * Date: 2017/12/29 11:17
 * Description:
 * <p>
 * "Remark": "",
 * "TypeID": 1.0,
 * "TypeName": "监区"
 */

public class ModuleType {
    /**
     * 备注
     */
    private String Remark;
    /**
     * 类型id
     */
    private String TypeID;
    /**
     * 类型名称
     */
    private String TypeName;

    /**
     * 层级,这个需要手机端自己设置(0,第一层;1,第二层,依次类推)
     */
    private int typeLevel;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String typeID) {
        TypeID = typeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(int typeLevel) {
        this.typeLevel = typeLevel;
    }
}
