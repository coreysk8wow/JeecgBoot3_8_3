import { BasicColumn, FormSchema } from "/@/components/Table";

//定义表格列字段
export const columns: BasicColumn[] = [
	{
		title: "产品名称",
		dataIndex: "productName",
	},
	{
		title: "品牌",
		dataIndex: "brandName",
	},
	{
		title: "品类",
		dataIndex: "categoryName",
	},
	{
		title: "创建时间",
		dataIndex: "createTime",
	},
];

export //自定义表单字段
const formSchemas: FormSchema[] = [
	{
		//标题名称
		label: "产品名称",
		//字段
		field: "productName",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "Input",
		//标题宽度,支持数字和字符串
		labelWidth: 70,
		//标题长度，超过位数隐藏
		labelLength: 10,
		//一列占比总共24，比如一行显示2列
		colProps: { span: 30 },
	},
	{
		//标题名称
		label: "品牌",
		//字段
		field: "brandName",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "Input",
		//标题宽度,支持数字和字符串
		labelWidth: 70,
		//标题长度，超过位数隐藏
		labelLength: 10,
		//一列占比总共24，比如一行显示2列
		colProps: { span: 30 },
	},
	{
		//标题名称
		label: "品类",
		//字段
		field: "categoryName",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "Input",
		//标题宽度,支持数字和字符串
		labelWidth: 70,
		//标题长度，超过位数隐藏
		labelLength: 10,
		//一列占比总共24，比如一行显示2列
		colProps: { span: 30 },
	},
];
