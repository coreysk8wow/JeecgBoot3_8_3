import { Dayjs } from "dayjs";

/*
 * 订单列表-搜索参数
*/
export interface IOrderListSearchParams {
    procNo?: string;
    orderNo?: string;
    beginDate?: Dayjs;
    endDate?: Dayjs;
}
