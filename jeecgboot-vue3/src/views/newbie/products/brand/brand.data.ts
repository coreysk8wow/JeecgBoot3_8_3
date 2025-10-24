import { BasicColumn, FormSchema } from "/@/components/Table";

//定义表格列字段
export const columns: BasicColumn[] = [
	{
		title: "品牌名称",
		dataIndex: "name",
	},
	{
		title: "创建时间",
		dataIndex: "createTime",
	},
];

//自定义表单字段
export const formSchemas: FormSchema[] = [
	{
		label: "主键",
		field: "id",
		component: "Input",
		show: false,
	},
	{
		//标题名称
		label: "品牌名称",
		//字段
		field: "name",
		//组件 支持组件详见 components/Form/src/types/index.ts 中的 ComponentType
		component: "Input",
		//标题宽度,支持数字和字符串
		labelWidth: 80,
		//标题长度，超过位数隐藏
		labelLength: 10,
		//一列占比总共24，比如一行显示2列
		colProps: { span: 30 },
		// 红星表示必填
		required: true,
		/* dynamicRules: ({ model, schema }) => {
            return [{ required: true, message: '请输入职务级别!' }];
        }, */
	},
];
