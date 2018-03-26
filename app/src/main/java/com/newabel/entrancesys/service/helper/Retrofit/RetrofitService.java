package com.newabel.entrancesys.service.helper.Retrofit;

import com.newabel.entrancesys.service.entity.Book;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by huan on 2017/11/14 08:51.
 */

public interface RetrofitService {
//https://api.douban.com/v2/book/search?q=西游记&tag=&start=0&count=1

    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count);

    @POST("api/Login")
    Observable<Map<String, Object>> login(@Query("UserID") String UserID, @Query("UserPwd") String UserPwd);

    @POST("api/User")
    Observable<Map<String, Object>> User(@Body RequestBody body);

    /**
     * 按条件查询门禁资料
     * 0-查询所有数据,1-按区域ID查询,2-按门禁编号查询,3-按门禁名称
     *
     * @param qryType
     * @param qryValue
     * @return
     */
    @GET("api/Door")
    Observable<Map<String, Object>> Door(@Query("QryType") String qryType, @Query("QryValue") String qryValue);

    @POST("api/Door")
    Observable<Map<String, Object>> DoorAdd(@Body RequestBody body);

    @PUT("api/Door")
    Observable<Map<String, Object>> doorModify(@Body RequestBody body);

    @DELETE("api/Door")
    Observable<Map<String, Object>> doorDelete(@Query("DoorID") int body);

    /**
     * 按条件查找区域资料
     *
     * @param qryType  0-查询所有数据,1-按上级区域ID查询,2-按区域编号查询,3-按区域名称
     * @param qryValue 对应数值
     * @return
     */

    @GET("api/Place")
    Observable<Map<String, Object>> Place(@Query("QryType") String qryType, @Query("QryValue") String qryValue);

    @POST("api/Place")
    Observable<Map<String, Object>> placeAdd(@Body RequestBody body);

    @PUT("api/Place")
    Observable<Map<String, Object>> placeModify(@Body RequestBody body);

    @DELETE("api/Place")
    Observable<Map<String, Object>> placeDelete(@Query("PlaceID") int body);

    /**************************************设备资料*********************************/

    @POST("api/Device")
    Observable<Map<String, Object>> DeviceAdd(@Body RequestBody body);

    @DELETE("api/Device")
    Observable<Map<String, Object>> DeviceDelete(@Query("DeviceID") int DeviceID);

    @PUT("api/Device")
    Observable<Map<String, Object>> DeviceModify(@Body RequestBody body);

    /**
     * 按条件查找设备资料
     *
     * @param qryType  0-查询所有数据,1-按区域ID查询,2-按设备编号查询,3-按设备名称
     * @param qryValue 对应数值
     * @return
     */
    @GET("api/Device")
    Observable<Map<String, Object>> Device(@Query("QryType") String qryType, @Query("QryValue") String qryValue);

    @GET("api/Device")
    Observable<Map<String, Object>> Device(@Query("DeviceID")int DeviceID);

    /**************************************人员资料*********************************/

    @DELETE("api/Employee")
    Observable<Map<String, Object>> EmployeeDelete(@Query("EmpID") int empId);

    @POST("api/Employee")
    Observable<Map<String, Object>> EmployeeAdd(@Body RequestBody body);

    @PUT("api/Employee")
    Observable<Map<String, Object>> empModify(@Body RequestBody body);

    @GET("api/Employee")
    Observable<Map<String, Object>> Employee();

    @GET("api/Employee")
    Observable<Map<String, Object>> Employee(@Query("id") String id);

    /**
     * 按条件查找人员资料
     * <p>
     * 0-查询所有数据,1-按机构ID查询,2-按人员编号查询,3-按人员姓名,4-按证件号
     *
     * @param qryType
     * @param qryValue
     * @return
     */
    @GET("api/Employee")
    Observable<Map<String, Object>> Employee(@Query("QryType") String qryType, @Query("QryValue") String qryValue);

    /*****************************************机构管理*********************************/

    @POST("api/Dept")
    Observable<Map<String, Object>> DeptAdd(@Body RequestBody body);

    @DELETE("api/Dept")
    Observable<Map<String, Object>> DeptDelete(@Query("DeptID") int DeptID);

    @PUT("api/Dept")
    Observable<Map<String, Object>> DeptModify(@Body RequestBody body);

    //获取所有机构类型资料
    @GET("api/DeptType")
    Observable<Map<String, Object>> DeptType();

    //获取指定机构类型资料
    @GET("api/DeptTyp")
    Observable<Map<String, Object>> DeptType(@Query("TypetID") String typeId);

    //获取所有机构id
    @GET("api/Dept")
    Observable<Map<String, Object>> Dept();

    /**
     * 按条件查找机构资料
     * 0-查询所有数据,1-按上级机构ID查询,2-按机构编号查询,3-按机构名称
     *
     * @param qryType
     * @param qryValue
     * @return
     */
    @GET("api/Dept")
    Observable<Map<String, Object>> Dept(@Query("QryType") String qryType, @Query("QryValue") String qryValue);

    @POST("facepp/v3/detect")
    Observable<Map<String,Object>> detect(@Body MultipartBody body);

}
