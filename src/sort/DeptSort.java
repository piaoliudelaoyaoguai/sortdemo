package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;

/**
 * <p>列表排序，按照树结构排序list（顶级无父节点）<p>
 * 排序前：122,13,121,1,131,12,132...
 * 无序的
 * [TestSort [id=122, name=三级b, parentid=12], TestSort [id=13, name=二级b, parentid=1], TestSort [id=121, name=三级a, parentid=12], TestSort [id=1, name=一级, parentid=null], TestSort [id=131, name=三级c, parentid=13], TestSort [id=12, name=二级a, parentid=1], TestSort [id=132, name=三级d, parentid=13]]
 * 
 * 排序后：1,13,131,132,12,122,121...
 * 按照树结构排序
 * [TestSort [id=1, name=一级, parentid=null], TestSort [id=13, name=二级b, parentid=1], TestSort [id=131, name=三级c, parentid=13], TestSort [id=132, name=三级d, parentid=13], TestSort [id=12, name=二级a, parentid=1], TestSort [id=122, name=三级b, parentid=12], TestSort [id=121, name=三级a, parentid=12]]
 * @version 1.0
 * @author li_hao
 * @date 2018年4月12日
 */
public class DeptSort {

    private List<Dept> resultList = new ArrayList<>();  //输出列表
    private List<Dept> deptList;  //输入列表
    
    /**
     * 排序
     * @param deptList
     */
    public DeptSort(List<Dept> deptList){
        this.deptList = deptList;
        
        for(Dept dept : this.deptList){
            if(StringUtils.isBlank(dept.getParentid())){  //当父级为空
                resultList.add(dept);  //当父级为空时即顶级，首先放入输出列表
                findChildren(dept);  //查询下级
            }
        }
    }
    
    /**
     * 查询下级
     * @param dept
     */
    private void findChildren(Dept dept){
        List<Dept> childrenList = new ArrayList<>();
        //遍历输入列表，查询下级
        for(Dept d : deptList){
            if(Objects.equals(dept.getId(), d.getParentid()))
                childrenList.add(d);
        }
        //遍历到最末端，无下级，退出遍历
        if(childrenList.isEmpty()){
            return;
        }
        //对下级进行遍历
        for(Dept d : childrenList){
            resultList.add(d);
            findChildren(d);
        }
    }
    
    public List<Dept> getResultList(){
        return resultList;
    }
    
    public static List<Dept> sort(List<Dept> originalList){
        return new DeptSort(originalList).getResultList();
    }
    
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        List<Dept> originalList = new ArrayList<Dept>();
    	originalList.add(new Dept("122", "三级b", "12"));
    	originalList.add(new Dept("13", "二级b", "1"));
    	originalList.add(new Dept("121", "三级a", "12"));
    	originalList.add(new Dept("1", "一级", null));
    	originalList.add(new Dept("131", "三级c", "13"));
    	originalList.add(new Dept("12", "二级a", "1"));
    	originalList.add(new Dept("132", "三级d", "13"));
        
        List<Dept> resultList = DeptSort.sort(originalList);
        System.out.println("输入列表："+ originalList);
        System.out.println("输出列表："+ resultList);
    }
}