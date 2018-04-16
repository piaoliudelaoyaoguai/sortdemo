package sort;

/**
 * <p>部门列表排序测试类<p>
 * @version 1.0
 * @author li_hao
 * @date 2018年4月12日
 */
public class Dept {

    private String id;  //id
    private String name;  //名称
    private String parentid;  //父级id
    
    public Dept(){
        super();
    }
    
    public Dept(String id, String name, String parentid) {
        super();
        this.id = id;
        this.name = name;
        this.parentid = parentid;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getParentid() {
        return parentid;
    }
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    @Override
    public String toString() {
        return "TestSort [id=" + id + ", name=" + name + ", parentid=" + parentid + "]";
    }
}