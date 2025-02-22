package cn.lili.modules.store.service;

import cn.lili.modules.store.entity.dos.StoreDetail;
import cn.lili.modules.store.entity.dto.StoreAfterSaleAddressDTO;
import cn.lili.modules.store.entity.dto.StoreSettingDTO;
import cn.lili.modules.store.entity.vos.StoreBasicInfoVO;
import cn.lili.modules.store.entity.vos.StoreDetailVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 店铺详细业务层
 *
 * @author pikachu
 * @date 2020-03-07 09:24:33
 */
public interface StoreDetailService extends IService<StoreDetail> {
    /**
     * 根据店铺ID获取店铺信息VO
     *
     * @param storeId 店铺ID
     * @return 店铺信息VO
     */
    StoreDetailVO getStoreDetailVO(String storeId);

    /**
     * 根据会员ID获取店铺信息VO
     *
     * @param memberId 会员ID
     * @return 店铺信息VO
     */
    StoreDetailVO getStoreDetailVOByMemberId(String memberId);

    /**
     * 根据店铺ID获取店铺信息DO
     *
     * @param storeId 店铺ID
     * @return 店铺信息DO
     */
    StoreDetail getStoreDetail(String storeId);

    /**
     * 修改商家设置
     *
     * @param storeSettingDTO 店铺设置信息
     * @return 店铺详情
     */
    Boolean editStoreSetting(StoreSettingDTO storeSettingDTO);

    /**
     * 获取店铺基本信息
     * 用于前端店铺信息展示
     *
     * @param storeId 店铺ID
     * @return 店铺基本信息
     */
    StoreBasicInfoVO getStoreBasicInfoDTO(String storeId);

    /**
     * 获取当前登录店铺售后收件地址
     *
     * @return 店铺售后收件地址
     */
    StoreAfterSaleAddressDTO getStoreAfterSaleAddressDTO();

    /**
     * 获取某一个店铺的退货收件地址信息
     *
     * @return 店铺售后收件地址
     */
    StoreAfterSaleAddressDTO getStoreAfterSaleAddressDTO(String id);

    /**
     * 修改当前登录店铺售后收件地址
     *
     * @return 店铺售后收件地址
     */
    boolean editStoreAfterSaleAddressDTO(StoreAfterSaleAddressDTO storeAfterSaleAddressDTO);

    /**
     * 修改店铺库存预警数量
     * @param stockWarning 库存预警数量
     * @return 操作状态
     */
    boolean updateStockWarning(Integer stockWarning);

    List goodsManagementCategory(String storeId);
}