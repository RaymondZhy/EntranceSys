package com.newabel.entrancesys.app.constants;

/**
 * Author:liuh
 * Date: 2017/12/1 11:08
 * Description:一些全局的常量数值
 */

public class Constant {

    /**
     * App请求的URL地址
     */
    public static String APP_SERVER_URL = "192.168.2.91";
//    public static String APP_SERVER_URL = "http://218.17.125.117";

    /**
     * App请求的端口号
     */
//    public static String APP_SERVER_PORT = "9998";
    public static String APP_SERVER_PORT = "8090";

    /**
     * 推送服务器地址
     */
    public static String ACTIVE_MQ_URL = "192.168.1.4";

    /**
     * 推送服务器端口号
     */
    public static int ACTIVE_MQ_PORT = 1883;

    /**
     * 已选中频道的json
     **/
    public static final String SELECTED_CHANNEL_JSON = "selectedChannelJson";
    /**
     * 未选中频道的json
     **/
    public static final String UNSELECTED_CHANNEL_JSON = "unselectedChannelJson";

    /**
     * 频道对应的请求参数
     */
    public static final String CHANNEL_CODE = "channelCode";
    public static final String IS_VIDEO_LIST = "isVideoList";

    /**
     * 图片占用标志
     */
    public final static String FLAG_POSITION_OCCUPY = "FLAG_POSITION_OCCUPY";

    /**
     * 图片传递数据的数据名称
     */
    public final static String IMAGE_DATA = "IMAGE_DATA";


    /**
     * 功能区块
     **/

    public static final int MODULE_ENTRANCE_DATA = 100;//门禁资料
    public static final int MODULE_ENTRANCE_EVENT = 200;//门禁事件
    public static final int MODULE_ENTRANCE_CONTROL = 300;//门禁控制
    public static final int MODULE_ZONE_MANAGE = 400;//区域管理
    public static final int MODULE_DEVICE_DATA = 500;//设备资料
    public static final int MODULE_DEPARTMENT_MANAGE = 600;//机构管理
    public static final int MODULE_EMPLOYEE_MANAGE = 700;//人员管理
    public static final int MODULE_REGISTER_REVIEW = 800;//注册审核
    public static final int MODULE_WORK_ORDER = 900;//我的工单


    /**
     * 以下五个为位临时变量，待有正式数据时可删。
     */
    public static final int REGISTER_DATA_FILLING = 1001; //待处理
    public static final int REGISTER_DATA_REVIEWING = 1002; //审核中
    public static final int REGISTER_DATA_PASS = 1003; //审核通过
    public static final int REGISTER_DATA_REFUSED = 1004; //暂不合作
    public static int REGISTER_DATA_STATE = 0; //当前状态


    public static final int RESULT_CODE_OK = 0;                  //成功
    public static final int RESULT_CODE_ERROR = 1;              //失败
    public static final int RESULT_CODE_PARENTNOTEXIST = 2;    //上级机构区域不存在
    public static final int RESULT_CODE_DEPTNOTEXIST = 3;      //机构不存在
    public static final int RESULT_CODE_DEPTEXIST = 4;         //机构已存在
    public static final int RESULT_CODE_EMPNOTEXIST = 5;        //人员不存在
    public static final int RESULT_CODE_EMPEXIST = 6;         //人员已存在
    public static final int RESULT_CODE_DEPTTYPENOTEXIST = 7;   //机构类型不存在
    public static final int RESULT_CODE_EMPTYPENOTEXIST = 8;    //人员类型不存在
    public static final int RESULT_CODE_PLACENOTEXIST = 9;       //区域不存在
    public static final int RESULT_CODE_PLACEEXIST = 10;          //区域已存在
    public static final int RESULT_CODE_DEVICENOTEXIST = 11;      //设备不存在
    public static final int RESULT_CODE_DEVICEEXIST = 12;        //设备已存在
    public static final int RESULT_CODE_DOORNOTEXIST = 13;        //门禁不存在
    public static final int RESULT_CODE_DOOREXIST = 14;           //门禁已存在
    public static final int RESULT_CODE_BALANCELESS = 23;       //余额不足
    public static final int RESULT_CODE_BILLNOTEXIST = 24;        //消费单号不存在
    public static final int RESULT_CODE_REFUNDOVER = 25;          //退款金额大于消费金额
    public static final int RESULT_CODE_REFUNDEND = 26;         //已经退款
    public static final int RESULT_CODE_USERUNLOGIN = 100;      //用户未登录
    public static final int RESULT_CODE_USEREXIST = 101;        //注册用户已存在
    public static final int RESULT_CODE_USERNOTEXIST = 102;       //登录用户不存在
    public static final int RESULT_CODE_USERPWDERROR = 103;       //登录密码错误
    public static final int RESULT_CODE_USERSTOPPED = 104;        //登录用户已停用
    public static final int RESULT_CODE_USEREXPIRE = 105;         //登录用户已过期
    public static final int RESULT_CODE_USERLOCKED = 106;         //登录用户已锁定
    public static final int RESULT_CODE_USERKEYERROR = 107;       //登录认证密钥错误
    public static final int RESULT_CODE_USERNOTRIGHT = 108;     //用户无权限

}
