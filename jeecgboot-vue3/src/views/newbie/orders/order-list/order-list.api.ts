import { defHttp } from "/@/utils/http/axios";

enum Api {
	getOrdersByPage = "/newbie/order/getAllByPage",
}

export function getOrdersByPage(params) {
    console.log("-----getOrdersByPage()  API called with params:", params);

    const searchParamsBody = {
        procNo: params.procNo, 
        orderNo: params.orderNo,
        beginDate: undefined,
        endDate: undefined,
    }
    if (params.createTime !== undefined && params.createTime !== null && params.createTime !== '') {
        const [beginDateStr, endDateStr] = params.createTime.split(',');
        const beginDate = beginDateStr ? beginDateStr.trim() : undefined;
        const endDate = endDateStr ? endDateStr.trim() : undefined;
        searchParamsBody.beginDate = beginDate;
        searchParamsBody.endDate = endDate;
    }
    console.log("-----Modified params for API call:", searchParamsBody);

    const reqParams = {
        pageNo: params.pageNo,
        pageSize: params.pageSize,
    };

	return defHttp.post({ 
        url: Api.getOrdersByPage, 

        // 对应SpringBoot controller的@RequestParam
        params: reqParams, 
        
        // 对应SpringBoot controller的@RequestBody
        data: searchParamsBody 
    });

}
