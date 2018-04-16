package sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

/**
 * 
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
public class DeptSort2 {

    private TreeMap<Integer, List<Dept>> treeMap;  //定义一个treeMap，key是等级，value是当前的等级对应的所有对象list
    private Integer level = 2;
    private List<Dept> resultList = new ArrayList();  //输出列表
    private List<Dept> deptList;  //输入列表
    
    /**
     * 排序
     * @param deptList
     */
    public DeptSort2(List<Dept> deptList) {
        this.deptList = deptList;

        for (Dept dept : this.deptList) {
            if (StringUtils.isBlank(dept.getParentid())) {  //当父级为空
                resultList.add(dept);  //当父级为空时即顶级，首先放入输出列表
                treeMap = new TreeMap<>();
                findChildren(dept);  //查询下级
                Iterator it = treeMap.keySet().iterator();  //迭代treeMap
                while (it.hasNext()) {  //检查序列中是否还有元素,如果迭代中还有元素返回true(因为treeMap中放的是2级和2级下面的所有list，所以只需要判断it.hashNext)
                    resultList.addAll(treeMap.get(it.next()));  //把treeMap中所有的list按照层级顺序添加到resultList中
                }
            }
        }
    }

    /**
     * 查询下级部门
     * 方法进去的时候就记录当前层级数，findchildren方法走完的时候，表示这一层已经没有逻辑了，递归回上一层，所以 this点level减一
     * @param dept
     */
    private void findChildren(Dept dept) {
        Integer level = this.level++;  //第一次进来时level值为2，this.level值为3
        try {
            List<Dept> childrenList = new ArrayList<>();
            //遍历输入列表，查询下级
            for (Dept d : deptList) {
                if (Objects.equals(dept.getId(), d.getParentid()))
                    childrenList.add(d);
            }
            //遍历到最末端，无下级，退出遍历
            if (childrenList.isEmpty()) {
                return;
            }
            //对下级进行遍历
            for (Dept d : childrenList) {
                addToMap(level, d); //向treeMap中添加等级和对应dept(第一次执行的level值为2)
                findChildren(d);  //查询下级，(比如：第一次进来时level值为2，this.level值为3，在进入此方法后，level为3，this.level为4，没查到则跳出，level减一)
            }
        } finally {
            this.level--;  //由于再次执行findChildren时，this.level的值+1了，那么在执行完毕后需要finally：this.level--
        }
    }
    
    /**
     * 向treeMap中添加等级和对应的List
     * @param level
     * @param dept
     */
    void addToMap(Integer level, Dept dept) {
        if (Objects.isNull(treeMap.get(level)))  
            treeMap.put(level, new ArrayList<Dept>());  //先判断下对应层级在map里有没有，没有就初始化，给个list

        treeMap.get(level).add(dept);  //若treeMap中有等级，则添加dept
    }

    public List<Dept> getResultList() {
        return resultList;
    }
    
    public static List<Dept> sort(List<Dept> originalList) {
        return new DeptSort2(originalList).getResultList();
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

        List<Dept> resultList = DeptSort2.sort(originalList);
        System.out.println("输入列表："+ originalList);
        System.out.println("输出列表："+ resultList);
    }
}
