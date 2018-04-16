package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;

/**
 * java8 Stream 优化递归
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
public class DeptSortJava8 {

    private List<Dept> deptList;

    public DeptSortJava8(List<Dept> deptList) {
        this.deptList = deptList;
    }

    public static List<Dept> sort(List<Dept> originalList) {
        return new DeptSortJava8(originalList).sort();
    }

    private List<Dept> sort() {
        return this.deptList.stream()
                .filter(d -> StringUtils.isBlank(d.getParentid()))
                .flatMap(d -> Stream.concat(Stream.of(d), findChildren(d)))
                .collect(Collectors.toList());
    }

    /**
     * 查询下级部门
     * @param dept
     */
    private Stream<Dept> findChildren(Dept dept) {
        return deptList.stream()
                .filter(d -> Objects.equals(dept.getId(), d.getParentid()))
                .flatMap(d -> Stream.concat(Stream.of(d), findChildren(d)));
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
         
        List<Dept> resultList = DeptSortJava8.sort(originalList);
        System.out.println("输入列表："+ originalList);
        System.out.println("输出列表："+ resultList);
    }   
}
