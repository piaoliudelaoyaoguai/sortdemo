package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 * java8 Stream 优化递归
 * <p>列表排序，按照层级从上到下排序list，同级的放一块（顶级无父节点）<p>
 * * 排序前：122,13,121,1,131,12,132...
 * 无序的
 * [TestSort [id=122, name=三级b, parentid=12], TestSort [id=13, name=二级b, parentid=1], TestSort [id=121, name=三级a, parentid=12], TestSort [id=1, name=一级, parentid=null], TestSort [id=131, name=三级c, parentid=13], TestSort [id=12, name=二级a, parentid=1], TestSort [id=132, name=三级d, parentid=13]]
 * 
 * 排序后：1,13,12,131,132,122,121...
 * 按照层级排序
 * [TestSort [id=1, name=一级, parentid=null], TestSort [id=13, name=二级b, parentid=1], TestSort [id=12, name=二级a, parentid=1], TestSort [id=131, name=三级c, parentid=13], TestSort [id=132, name=三级d, parentid=13], TestSort [id=122, name=三级b, parentid=12], TestSort [id=121, name=三级a, parentid=12]]
 * @version 1.0
 * @author li_hao
 * @date 2018年4月12日
 */
public class DeptSortJava82 {

    private List<Dept> deptList;

    private List<Dept> resultList = new ArrayList<>();

    public DeptSortJava82(List<Dept> deptList) {
        this.deptList = deptList;
    }

    public static List<Dept> sort(List<Dept> originalList) {
        return new DeptSortJava82(originalList).sort();
    }

    private List<Dept> sort() {
        this.deptList.stream()
                .filter(d -> StringUtils.isBlank(d.getParentid()))
                .forEach(d -> {
                    resultList.add(d);
                    findChildren(d);
                });
        return resultList;
    }

    /**
     * 查询下级部门
     *
     * @param dept
     */
    private void findChildren(Dept dept) {
        List<Dept> childrenList = deptList.stream()
                .filter(d -> Objects.equals(dept.getId(), d.getParentid()))
                .collect(Collectors.toList());

        if (childrenList.isEmpty())
            /* 跳出递归 */
            return;
        else
            childrenList.forEach(d -> {
                resultList.add(d);
                findChildren(d);
            });
    }

    public static void main(String[] args) {
    	List<Dept> originalList = new ArrayList<Dept>();
    	originalList.add(new Dept("122", "三级b", "12"));
    	originalList.add(new Dept("13", "二级b", "1"));
    	originalList.add(new Dept("121", "三级a", "12"));
    	originalList.add(new Dept("1", "一级", null));
    	originalList.add(new Dept("131", "三级c", "13"));
    	originalList.add(new Dept("12", "二级a", "1"));
    	originalList.add(new Dept("132", "三级d", "13"));
    	
        List<Dept> resultList = DeptSortJava82.sort(originalList);
        System.out.println("输入列表："+ originalList);
        System.out.println("输出列表："+ resultList);
    }
}
