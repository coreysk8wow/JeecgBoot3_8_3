import dayjs from "dayjs";
import { defHttp } from "/@/utils/http/axios";

enum Api {
	getOrdersByPage = "/newbie/order/getAllByPage",
}

export function getOrdersByPage(params) {
    console.log("-----getOrdersByPage()  API called with params:", params);

    let modifiedParams = params;

    if (params.createTime !== undefined && params.createTime !== null && params.createTime !== '') {
        const [beginDateStr, endDateStr] = params.createTime.split(',');
        const beginDate = beginDateStr ? beginDateStr.trim() : undefined;
        const endDate = endDateStr ? endDateStr.trim() : undefined;
        modifiedParams = {
            ...params,
            beginDate: beginDate, 
            endDate: endDate,
        };
    }

    console.log("-----Modified params for API call:", modifiedParams);

	return defHttp.post({ url: Api.getOrdersByPage, params: modifiedParams });

}
