package cn.lili.modules.statistics.mapper;

import cn.lili.modules.order.order.entity.dos.StoreFlow;
import cn.lili.modules.statistics.model.vo.CategoryStatisticsDataVO;
import cn.lili.modules.statistics.model.vo.GoodsStatisticsDataVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品统计数据处理层
 *
 * @author Bulbasaur
 * @date 2020/11/17 7:34 下午
 */
public interface GoodsStatisticsDataMapper extends BaseMapper<StoreFlow> {

    //商品统计
    @Select("SELECT goods_id,goods_name,SUM(final_price) AS price,SUM(num) AS num FROM li_store_flow ${ew.customSqlSegment}")
    List<GoodsStatisticsDataVO> getGoodsStatisticsData(IPage<GoodsStatisticsDataVO> page, @Param(Constants.WRAPPER) Wrapper<GoodsStatisticsDataVO> queryWrapper);

    //分类统计
    @Select("SELECT category_id,category_name,SUM(price) AS price,SUM(num) AS num FROM li_store_flow ${ew.customSqlSegment}")
    List<CategoryStatisticsDataVO> getCateGoryStatisticsData(@Param(Constants.WRAPPER) Wrapper<CategoryStatisticsDataVO> queryWrapper);
}