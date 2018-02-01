package com.newabel.entrancesys.ui.handler;

import android.content.Context;
import android.content.Intent;

import com.newabel.entrancesys.app.constants.Constant;
import com.newabel.entrancesys.service.entity.Content;
import com.newabel.entrancesys.ui.activity.ModuleActivity;
import com.newabel.entrancesys.ui.activity.RegisterReviewActivity;
import com.newabel.entrancesys.ui.activity.WorkOrderActivity;

/**
 * Date: 2017/12/13 14:04
 * Description:打开activity的帮助类
 */

public class OpenHandler {

    /**
     * HomeFragment中功能模块的打开
     *
     * @param context
     * @param content
     */
    public static void openActivity(Context context, Content content) {

        switch (content.getContentType()) {
            case Constant.MODULE_ENTRANCE_DATA:
            case Constant.MODULE_ENTRANCE_EVENT:
            case Constant.MODULE_ENTRANCE_CONTROL:
            case Constant.MODULE_ZONE_MANAGE:
            case Constant.MODULE_DEVICE_DATA:
            case Constant.MODULE_DEPARTMENT_MANAGE:
            case Constant.MODULE_EMPLOYEE_MANAGE: {
                Intent intent = new Intent(context, ModuleActivity.class);
                intent.putExtra("MODULE_CODE", content.getContentType());
                context.startActivity(intent);
                break;
            }
            case Constant.MODULE_REGISTER_REVIEW: {
                Intent intent = new Intent(context, RegisterReviewActivity.class);
                context.startActivity(intent);
                break;
            }
            case Constant.MODULE_WORK_ORDER:
                Intent intent = new Intent(context, WorkOrderActivity.class);
                context.startActivity(intent);
                break;
            default:

                break;
        }
    }
}
