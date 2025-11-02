import { FormSchema } from "/@/components/Form/src/types/form";
import { BasicColumn } from "/@/components/Table/src/types/table";

export const orderListColumns: BasicColumn[] = [
	{
		title: "订单表ID",
		dataIndex: "id",
		ifShow: false,
	},
	{
		title: "流程编号",
		dataIndex: "procNo",
		align: "center",
		width: "20%",
	},
	{
		title: "订单号",
		dataIndex: "orderNo",
		align: "center",
		width: "15%",
	},
	{
		title: "产品名称",
		dataIndex: "productName",
		width: "15%",
		align: "center",
	},
	{
		title: "箱数",
		dataIndex: "qtyBox",
		width: "20%",
		align: "center",
	},
	{
		title: "申请人",
		dataIndex: "requester",
		width: "15%",
		align: "center",
	},
	{
		title: "创建时间",
		dataIndex: "createTime",
		width: "15%",
		align: "center",
	},
];

//表单搜索字段
export const searchFormSchema: FormSchema[] = [
	{
		label: "流程编号", //显示label
		field: "procNo", //查询字段
		component: "JInput", //渲染的组件
		componentProps: {
			// 查询类型['like','ne','ge','le'] 分别是模糊，不等于，大于，小于，默认like,
            // 如果不想添加任何规则，请设置type="",即能走等于查询（默认like）
            type: "",
			// 	是否自动去空格 默认false
            trim: true,
		},
	},
	{
		label: "订单号",
		field: "orderNo",
		component: "JInput",
        componentProps: {
			// 查询类型['like','ne','ge','le'] 分别是模糊，不等于，大于，小于，默认like,
            // 如果不想添加任何规则，请设置type="",即能走等于查询（默认like）
            type: "",
			// 	是否自动去空格 默认false
            trim: true,
		},
	},
	{
		label: "创建时段",
		field: "createTime",
		component: "RangeDate",
	},
];
